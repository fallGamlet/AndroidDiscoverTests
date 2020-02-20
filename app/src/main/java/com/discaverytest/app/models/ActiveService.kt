package com.discaverytest.app.models

import org.joda.time.DateTime

data class ActiveService(
    var id: String = "",
    var title: String = "",
    var balance: Float,
    var type: ServiceType = ServiceType.SINGLE_SERVICE,
    var status: ServiceStatus = ServiceStatus.NO_STATUS,
    var endDate: DateTime? = null,
    var statusDate: DateTime? = null
) {

    enum class ServiceStatus {
        ACTIVE,
        CLOSED,
        FROZEN,
        LOCKED,
        NOT_ACTIVE,
        NO_STATUS
    }

    enum class ServiceType {
        MEMBERSHIP,
        PACKAGE,
        SINGLE_SERVICE
    }
}
