package com.jihunyun.movieposterlotte.data


import com.jihunyun.movieposterlotte.utils.getDateFormat
import com.google.gson.annotations.SerializedName

data class MoviePosterResponse(
    @SerializedName("Data") val dataList: List<Data>? = null,
    @SerializedName("KMAQuery") val kMAQuery: String? = null,
    @SerializedName("Query") val query: String? = null,
    @SerializedName("TotalCount") val totalCount: Int
) {
    fun getData() = dataList?.getOrNull(0)
}

data class Data(
    @SerializedName("CollName") val collName: String? = null,
    @SerializedName("Count") val count: Int,
    @SerializedName("Result") val result: List<Result>? = null,
    @SerializedName("TotalCount") val totalCount: Int
)

data class Result(
    @SerializedName("ALIAS") val aLIAS: String? = null,
    @SerializedName("actors") val actors: Actors? = null,
    @SerializedName("audiAcc") val audiAcc: String? = null,
    @SerializedName("Awards1") val awards1: String? = null,
    @SerializedName("Awards2") val awards2: String? = null,
    @SerializedName("Codes") val codes: Codes? = null,
    @SerializedName("CommCodes") val commCodes: CommCodes? = null,
    @SerializedName("company") val company: String? = null,
    @SerializedName("DOCID") val dOCID: String? = null,
    @SerializedName("directors") val directors: Directors? = null,
    @SerializedName("episodes") val episodes: String? = null,
    @SerializedName("fLocation") val fLocation: String? = null,
    @SerializedName("genre") val genre: String? = null,
    @SerializedName("keywords") val keywords: String? = null,
    @SerializedName("kmdbUrl") val kmdbUrl: String? = null,
    @SerializedName("modDate") val modDate: String? = null,
    @SerializedName("movieId") val movieId: String? = null,
    @SerializedName("movieSeq") val movieSeq: String? = null,
    @SerializedName("nation") val nation: String? = null,
    @SerializedName("openThtr") val openThtr: String? = null,
    @SerializedName("plots") val plots: Plots? = null,
    @SerializedName("posters") val posters: String? = null,
    @SerializedName("prodYear") val prodYear: String? = null,
    @SerializedName("ratedYn") val ratedYn: String? = null,
    @SerializedName("rating") val rating: String? = null,
    @SerializedName("ratings") val ratings: Ratings? = null,
    @SerializedName("regDate") val regDate: String? = null,
    @SerializedName("repRatDate") val repRatDate: String? = null,
    @SerializedName("repRlsDate") val repRlsDate: String? = null,
    @SerializedName("runtime") val runtime: String? = null,
    @SerializedName("salesAcc") val salesAcc: String? = null,
    @SerializedName("screenArea") val screenArea: String? = null,
    @SerializedName("screenCnt") val screenCnt: String? = null,
    @SerializedName("soundtrack") val soundtrack: String? = null,
    @SerializedName("staffs") val staffs: Staffs? = null,
    @SerializedName("stat") val stat: List<Stat?>? = null,
    @SerializedName("statDate") val statDate: String? = null,
    @SerializedName("statSouce") val statSouce: String? = null,
    @SerializedName("stlls") val stlls: String? = null,
    @SerializedName("themeSong") val themeSong: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("titleEng") val titleEng: String? = null,
    @SerializedName("titleEtc") val titleEtc: String? = null,
    @SerializedName("titleOrg") val titleOrg: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("use") val use: String? = null,
    @SerializedName("vods") val vods: Vods? = null
) {
    fun getPosterImgs(): List<String>? = posters?.split("|")
    fun getReleaseDate(): String? = getDateFormat(repRlsDate ?: "").toString()
}

data class Actors(
    @SerializedName("actor") val actor: List<Actor>? = null
) {
    data class Actor(
        @SerializedName("actorEnNm") val actorEnNm: String? = null,
        @SerializedName("actorId") val actorId: String? = null,
        @SerializedName("actorNm") val actorNm: String? = null
    )
}

data class Codes(
    @SerializedName("Code") val code: List<Code>? = null
) {
    data class Code(
        @SerializedName("CodeNm") val codeNm: String? = null,
        @SerializedName("CodeNo") val codeNo: String? = null
    )
}

data class CommCodes(
    @SerializedName("CommCode") val commCode: List<CommCode>? = null
) {
    data class CommCode(
        @SerializedName("CodeNm") val codeNm: String? = null,
        @SerializedName("CodeNo") val codeNo: String? = null
    )
}

data class Directors(
    @SerializedName("director") val director: List<Director>? = null
) {
    fun getDirector(): String {
        var name = ""
        director?.forEachIndexed { index, data ->
            name += "${data.directorNm} "
        }
        return name
    }

    data class Director(
        @SerializedName("directorEnNm") val directorEnNm: String? = null,
        @SerializedName("directorId") val directorId: String? = null,
        @SerializedName("directorNm") val directorNm: String? = null
    )
}

data class Plots(
    @SerializedName("plot") val plot: List<Plot>? = null
) {
    data class Plot(
        @SerializedName("plotLang") val plotLang: String? = null,
        @SerializedName("plotText") val plotText: String? = null
    )
}

data class Ratings(
    @SerializedName("rating") val rating: List<Rating>? = null
) {
    data class Rating(
        @SerializedName("ratingDate") val ratingDate: String? = null,
        @SerializedName("ratingGrade") val ratingGrade: String? = null,
        @SerializedName("ratingMain") val ratingMain: String? = null,
        @SerializedName("ratingNo") val ratingNo: String? = null,
        @SerializedName("releaseDate") val releaseDate: String? = null,
        @SerializedName("runtime") val runtime: String? = null
    )
}

data class Staffs(
    @SerializedName("staff") val staff: List<Staff>? = null
) {
    data class Staff(
        @SerializedName("staffEnNm") val staffEnNm: String? = null,
        @SerializedName("staffEtc") val staffEtc: String? = null,
        @SerializedName("staffId") val staffId: String? = null,
        @SerializedName("staffNm") val staffNm: String? = null,
        @SerializedName("staffRole") val staffRole: String? = null,
        @SerializedName("staffRoleGroup") val staffRoleGroup: String? = null
    )
}

data class Stat(
    @SerializedName("audiAcc") val audiAcc: String? = null,
    @SerializedName("salesAcc") val salesAcc: String? = null,
    @SerializedName("screenArea") val screenArea: String? = null,
    @SerializedName("screenCnt") val screenCnt: String? = null,
    @SerializedName("statDate") val statDate: String? = null,
    @SerializedName("statSouce") val statSouce: String? = null
)

data class Vods(
    @SerializedName("vod") val vod: List<Vod>? = null
) {
    data class Vod(
        @SerializedName("vodClass") val vodClass: String? = null,
        @SerializedName("vodUrl") val vodUrl: String? = null
    )
}