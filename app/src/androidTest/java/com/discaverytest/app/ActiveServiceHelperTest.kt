package com.discaverytest.app

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.discaverytest.app.models.ActiveService
import com.discaverytest.app.utils.ActiveServiceHelper

import org.joda.time.DateTime
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActiveServiceHelperTest {

    private val datePattern = "dd.MM.yyyy"
    private lateinit var context: Context

    @Before
    fun prepareData() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun getStatus() {
        testStatusText(createService(ActiveService.ServiceStatus.ACTIVE))
        testStatusText(createService(ActiveService.ServiceStatus.CLOSED))
        testStatusText(createService(ActiveService.ServiceStatus.FROZEN))
        testStatusText(createService(ActiveService.ServiceStatus.LOCKED))
        testStatusText(createService(ActiveService.ServiceStatus.NOT_ACTIVE))
        testStatusText(createService(ActiveService.ServiceStatus.NO_STATUS))
        testStatusText(createService(ActiveService.ServiceStatus.ACTIVE).apply { statusDate = null; endDate = null })
        testStatusText(createService(ActiveService.ServiceStatus.CLOSED).apply { statusDate = null; endDate = null })
        testStatusText(createService(ActiveService.ServiceStatus.FROZEN).apply { statusDate = null; endDate = null })
        testStatusText(createService(ActiveService.ServiceStatus.LOCKED).apply { statusDate = null; endDate = null })
        testStatusText(createService(ActiveService.ServiceStatus.NOT_ACTIVE).apply { statusDate = null; endDate = null })
        testStatusText(createService(ActiveService.ServiceStatus.NO_STATUS).apply { statusDate = null; endDate = null })
    }

    private fun createService(status: ActiveService.ServiceStatus): ActiveService {
        return ActiveService(
            id = "0",
            title = "test service",
            type = ActiveService.ServiceType.MEMBERSHIP,
            balance = 0f,
            status = status,
            endDate = DateTime.now(),
            statusDate = DateTime.now()
        )
    }

    private fun testStatusText(service: ActiveService) {
        assertEquals(
                getProfileNewViewHolderStatusText(context, service, datePattern),
                ActiveServiceHelper.getStatus(context, datePattern, service.status, service.statusDate, service.endDate)
        )
        assertEquals(
                getProfileServiceDetailsStatusText(context, service.status, datePattern, service.statusDate),
                ActiveServiceHelper.getStatus(context, datePattern, service.status, service.statusDate)
        )
        assertEquals(
                getPersonalInfoPreviewStatusText(context, service, datePattern),
                ActiveServiceHelper.getStatus(context, datePattern, service.status, service.statusDate, service.endDate)
        )
    }

    private fun getProfileNewViewHolderStatusText(context: Context, service: ActiveService, datePattern: String): String {
        return when (service.status) {
            ActiveService.ServiceStatus.ACTIVE ->
                context.getString(R.string.active_service_status_active) +
                service.endDate?.let { " " + context.getString(R.string.active_service_status_until_template, it.toString(datePattern)) } ?: ""
            ActiveService.ServiceStatus.CLOSED ->
                context.getString(R.string.active_service_status_closed) +
                service.endDate?.let { " " + context.getString(R.string.active_service_status_until_template, it.toString(datePattern)) } ?: ""
            ActiveService.ServiceStatus.FROZEN ->
                context.getString(R.string.active_service_status_frozen) +
                service.statusDate?.let { " " + context.getString(R.string.active_service_status_until_template, it.toString(datePattern)) } ?: ""
            ActiveService.ServiceStatus.LOCKED -> context.getString(R.string.active_service_status_locked)
            ActiveService.ServiceStatus.NOT_ACTIVE -> context.getString(R.string.active_service_status_not_active)
            else -> ""
        }
    }

    private fun getProfileServiceDetailsStatusText(context: Context, status: ActiveService.ServiceStatus, datePattern: String, statusDate: DateTime?): String {
        return when (status) {
            ActiveService.ServiceStatus.ACTIVE -> context.getString(R.string.active_service_status_active)
            ActiveService.ServiceStatus.CLOSED -> context.getString(R.string.active_service_status_closed)
            ActiveService.ServiceStatus.FROZEN -> context.getString(R.string.active_service_status_frozen) +
                    statusDate?.let { " " + context.getString(R.string.active_service_status_until_template, it.toString(datePattern)) }
            ActiveService.ServiceStatus.LOCKED -> context.getString(R.string.active_service_status_locked)
            ActiveService.ServiceStatus.NOT_ACTIVE -> context.getString(R.string.active_service_status_not_active)
            else -> ""
        }
    }

    private fun getPersonalInfoPreviewStatusText(context: Context, service: ActiveService, datePattern: String): String {
        return when (service.status) {
            ActiveService.ServiceStatus.ACTIVE ->
                context.getString(R.string.active_service_status_active) +
                service.endDate?.let { " " + context.getString(R.string.active_service_status_until_template, it.toString(datePattern)) } ?: ""
            ActiveService.ServiceStatus.CLOSED ->
                context.getString(R.string.active_service_status_closed) +
                service.endDate?.let { " "+ context.getString(R.string.active_service_status_until_template, it.toString(datePattern)) } ?: ""
            ActiveService.ServiceStatus.FROZEN ->
                context.getString(R.string.active_service_status_frozen) +
                service.statusDate?.let { " "+ context.getString(R.string.active_service_status_until_template, it.toString(datePattern)) } ?: ""
            ActiveService.ServiceStatus.LOCKED -> context.getString(R.string.active_service_status_locked)
            ActiveService.ServiceStatus.NOT_ACTIVE -> context.getString(R.string.active_service_status_not_active)
            else -> ""
        }
    }
}
