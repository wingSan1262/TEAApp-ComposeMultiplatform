package domain.auth.model


data class RegisterErrorModel(
    var namaDepan    : ArrayList<String> = arrayListOf(),
    var namaBelakang : ArrayList<String> = arrayListOf(),
    var gender       : ArrayList<String> = arrayListOf(),
    var nik          : ArrayList<String> = arrayListOf(),
    var email        : ArrayList<String> = arrayListOf(),
    var nomorTelepon : ArrayList<String> = arrayListOf(),
    var tempatLahir  : ArrayList<String> = arrayListOf(),
    var tanggalLahir : ArrayList<String> = arrayListOf()
)