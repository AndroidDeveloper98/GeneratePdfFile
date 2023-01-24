package com.innovative.generatepdffile.datamodel

data class Inspection(
    var location: String = "",
    var title: String = "",
    var assignTo: String = "",
    var status: String = "",
    var actionDate: String = "24-jan-2023",
    var description: String = "Message",
    var inspectionPhotoPath: String = "",
    var qrCodePath: String = "",
)