package com.innovative.generatepdffile.datamodel

data class Inspection(
    var location: String = "",
    var title: String = "",
    var assignTo: String = "",
    var status: String = "",
    var actionDate: String = "",
    var description: String = "",
    var inspectionPhotoPath: String = "",
    var qrCodePath: String = "",
)