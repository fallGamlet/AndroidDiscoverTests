package com.discaverytest.app.utils

import android.content.Context
import com.discaverytest.app.R
import com.discaverytest.app.models.ActiveService
import org.joda.time.DateTime

object ActiveServiceHelper {

    fun getStatus(context: Context, datePattern: String, status: ActiveService.ServiceStatus, statusDate: DateTime? = null, endDate: DateTime? = null): String {
        return when (status) {
            ActiveService.ServiceStatus.ACTIVE -> context.getStatusString(R.string.active_service_status_active, R.string.active_service_status_until_template, datePattern, endDate)
            ActiveService.ServiceStatus.CLOSED -> context.getStatusString(R.string.active_service_status_closed, R.string.active_service_status_until_template, datePattern, endDate)
            ActiveService.ServiceStatus.FROZEN -> context.getStatusString(R.string.active_service_status_frozen, R.string.active_service_status_until_template, datePattern, statusDate)
            ActiveService.ServiceStatus.LOCKED -> context.getString(R.string.active_service_status_locked)
            ActiveService.ServiceStatus.NOT_ACTIVE -> context.getString(R.string.active_service_status_not_active)
            else -> ""
        }
    }

    private fun Context.getStatusString(statusResId: Int, textResId: Int, datePattern: String, date: DateTime?): String {
        return getString(statusResId) +
                (date?.toString(datePattern)?.let { " " + getString(textResId, it) } ?: "")
    }

}
