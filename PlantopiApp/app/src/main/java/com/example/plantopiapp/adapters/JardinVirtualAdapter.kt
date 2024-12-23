import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.plantopiapp.R
import com.example.plantopiapp.WateringReminderWorker
import com.example.plantopiapp.dataclases.JardinVirtual
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.concurrent.TimeUnit
import java.time.format.DateTimeFormatter

// Adaptador para mostrar las plantas en un RecyclerView
class JardinVirtualAdapter(
    private val plants: List<JardinVirtual>, // Lista de plantas a mostrar
    private val context: Context // Contexto de la aplicación o actividad
) : RecyclerView.Adapter<JardinVirtualAdapter.JardinVirtualViewHolder>() {

    // Formateador para mostrar las fechas en un formato legible
    companion object {
        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    }

    // ViewHolder que contiene las vistas para cada elemento del RecyclerView
    class JardinVirtualViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.plant_image) // Imagen de la planta
        val plantName: TextView = itemView.findViewById(R.id.plant_name) // Nombre de la planta
        val plantCommonName: TextView = itemView.findViewById(R.id.plant_common_name) // Nombre común
        val wateringFrequency: TextView = itemView.findViewById(R.id.watering_frequency) // Frecuencia de riego
        val nextWateringDate: TextView = itemView.findViewById(R.id.next_watering_date) // Próxima fecha de riego
        val reminderStatus: TextView = itemView.findViewById(R.id.reminder_status) // Estado del recordatorio
        val activateReminderButton: Button = itemView.findViewById(R.id.activate_reminder_button) // Botón para activar recordatorio
    }

    // Infla el diseño del elemento y crea el ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JardinVirtualViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant_notification, parent, false)
        return JardinVirtualViewHolder(view)
    }

    // Vincula los datos de cada planta al ViewHolder
    override fun onBindViewHolder(holder: JardinVirtualViewHolder, position: Int) {
        val plant = plants[position]

        holder.plantName.text = plant.nombrePlanta
        holder.plantCommonName.text = plant.nombreComun
        holder.wateringFrequency.text = "Frecuencia de riego: ${plant.frecuencia_de_riego} días"
        holder.nextWateringDate.text = "Próximo riego: ${plant.proximo_riego.format(dateFormatter)}"

        // Verifica si el recordatorio está activo
        isReminderActive(context, plant.id) { isActive ->
            holder.reminderStatus.text = if (isActive) {
                "Recordatorio Activo"
            } else {
                "Sin Recordatorio"
            }
        }

        // Activa un recordatorio al hacer clic en el botón
        holder.activateReminderButton.setOnClickListener {
            scheduleReminder(context, plant)
            holder.reminderStatus.text = "Recordatorio Activo"
        }
    }

    // Devuelve el número total de elementos en la lista
    override fun getItemCount(): Int = plants.size

    // Programa un recordatorio para la planta usando WorkManager
    private fun scheduleReminder(context: Context, plant: JardinVirtual) {
        val delayMillis = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Calcula el retraso en milisegundos usando LocalDateTime (API 26+)
            Duration.between(LocalDateTime.now(), plant.proximo_riego).toMillis()
        } else {
            // Calcula el retraso usando Calendar para compatibilidad con APIs más bajas
            val now = Calendar.getInstance().timeInMillis
            val future = plant.proximo_riego
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
            future - now
        }

        if (delayMillis > 0) {
            // Crea un WorkRequest con el retraso especificado
            val workRequest = OneTimeWorkRequestBuilder<WateringReminderWorker>()
                .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                .setInputData(
                    Data.Builder()
                        .putString("plantName", plant.nombrePlanta) // Datos del recordatorio
                        .putString("reminderTime", plant.proximo_riego.toString())
                        .build()
                )
                .addTag("reminder_${plant.id}") // Etiqueta única para identificar el trabajo
                .build()

            // Encola el WorkRequest en WorkManager
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }

    // Verifica si un recordatorio está activo usando su etiqueta
    private fun isReminderActive(context: Context, plantId: Int, callback: (Boolean) -> Unit) {
        val workManager = WorkManager.getInstance(context)
        workManager.getWorkInfosByTagLiveData("reminder_$plantId").observeForever { workInfos ->
            val isActive = workInfos.any { workInfo ->
                workInfo.state == WorkInfo.State.ENQUEUED || workInfo.state == WorkInfo.State.RUNNING
            }
            callback(isActive)
        }
    }
}
