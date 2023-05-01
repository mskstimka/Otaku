package com.example.animator_data.mapper

import com.example.animator_data.database.models.LocalAnimePosterEntity
import com.example.animator_data.network.models.*
import com.example.animator_data.network.models.user.*
import com.example.animator_data.network.models.user.RateResponse
import com.example.domain.*
import com.example.domain.models.*
import com.example.domain.models.characters.CharacterDetailsEntity
import com.example.domain.models.details.AnimeDetailsEntity
import com.example.domain.models.details.Translations
import com.example.domain.models.details.franchise.AnimeDetailsFranchisesEntity
import com.example.domain.models.details.roles.AnimeDetailsRolesEntity
import com.example.domain.models.details.roles.Character
import com.example.domain.models.details.roles.Person
import com.example.domain.models.details.screenshots.AnimeDetailsScreenshotsEntity
import com.example.domain.models.poster.AnimePosterEntity
import com.example.domain.models.user.*


fun AnimeDetailsEntityResponse.toAnimeDetailsEntity(): AnimeDetailsEntity = AnimeDetailsEntity(
    aired_on = this.aired_on,
    description = this.description,
    description_html = this.description_html,
    episodes = this.episodes,
    episodes_aired = this.episodes_aired,
    genres = MapperUtils.checkGenresAdapter(this.genres),
    id = this.id,
    image = this.image,
    kind = this.kind,
    name = this.name,
    russian = this.russian,
    score = this.score,
    screenshots = this.screenshots,
    status = this.status,
    statusColor = MapperUtils.checkStatusColor(this.status),
    studios = MapperUtils.checkStudioList(this.studios),
    videos = MapperUtils.checkVideoList(this.videos)
)


fun List<AnimeDetailsScreenshotsEntityResponse>.toAnimeDetailsScreenshotsEntity(): List<AnimeDetailsScreenshotsEntity> {

    var screenshots: MutableList<AnimeDetailsScreenshotsEntity> = mutableListOf()

    when (this) {
        emptyList<AnimeDetailsScreenshotsEntityResponse>() -> {
            screenshots = mutableListOf(DEFAULT_SCREENSHOT)
        }
        else -> this.forEach {
            screenshots.add(
                AnimeDetailsScreenshotsEntity(
                    original = it.original,
                    preview = it.preview
                )
            )
        }
    }

    return screenshots
}

fun AnimeDetailsFranchisesEntityResponse.toListAnimeDetailsFranchisesEntity(): List<AnimeDetailsFranchisesEntity> {
    var list: MutableList<AnimeDetailsFranchisesEntity> = mutableListOf()

    when (this.nodes) {
        emptyList<Node>() -> list = mutableListOf(DEFAULT_FRANCHISE)
        else -> this.nodes.forEach {
            list.add(
                AnimeDetailsFranchisesEntity(
                    date = it.date,
                    id = it.id,
                    image_url = it.image_url,
                    kind = it.kind,
                    name = it.name,
                    url = it.image_url,
                    year = it.year
                )
            )
        }

    }
    return list
}


fun List<AnimeDetailsRolesEntityResponse>.toListAnimeRolesEntity(): AnimeDetailsRolesEntity {

    val personNotNull = this.mapNotNull { it.person }
    val listPerson = if (personNotNull == emptyList<Person>()) {
        listOf(DEFAULT_PERSON)
    } else personNotNull

    val characterNotNull = this.mapNotNull { it.character }
    val listCharacters = if (characterNotNull == emptyList<Character>()) {
        listOf(DEFAULT_CHARACTER)
    } else characterNotNull


    return AnimeDetailsRolesEntity(character = listCharacters.reversed(), person = listPerson)
}


fun CharacterDetailsResponse.toCharacterDetailsEntity(): CharacterDetailsEntity {
    return CharacterDetailsEntity(
        this.id,
        this.name,
        this.nameRu,
        this.image,
        this.url,
        this.nameAlt,
        this.nameJp,
        this.description,
        this.description_html,
        this.seyu,
        this.animes.toListFrachisesEntity()
    )
}


fun List<WorkResponse?>.toListWorkEntity(): List<WorkEntity> {

    return if (this.isNotEmpty()) {
        this.map {
            if (it?.anime != null) {
                WorkEntity(
                    anime = it.anime.toAnimePosterEntity(),
                    role = it.role
                )
            } else {
                null
            }
        }.filterNotNull()
    } else {
        emptyList()
    }
}


fun List<SeyuWorksResponse>.toListSeyuWork(): List<SeyuWorks> {
    return if (this.isNotEmpty()) {
        this.map { SeyuWorks(characters = it.characters) }
    } else {
        emptyList()
    }
}

fun PersonResponse.toPersonEntity(): PersonEntity {
    return PersonEntity(
        id = this.id,
        name = this.name,
        nameRu = this.nameRu ?: "",
        nameJp = this.nameJp ?: "",
        image = this.image,
        url = this.url ?: "",
        jobTitle = this.jobTitle ?: "",
        birthDay = "${this.birthDay?.day}.${this.birthDay?.month}.${this.birthDay?.year}",
        works = this.works?.toListWorkEntity() ?: emptyList(),
        roles = this.roles?.toListSeyuWork() ?: emptyList()
    )
}

fun List<CharacterDetailsSimiliarResponse>.toListFrachisesEntity(): List<AnimeDetailsFranchisesEntity> {
    return this.map {
        AnimeDetailsFranchisesEntity(
            id = it.id,
            image_url = SHIKIMORI_URL + it.image?.original,
            date = null,
            kind = it.kind,
            name = it.name,
            url = it.url,
            year = null
        )
    }
}

fun List<AnimePosterEntityResponse>.toListAnimePosterEntity(): List<AnimePosterEntity> {
    return this.map {
        AnimePosterEntity(
            id = it.id,
            image = it.image.toImage(),
            name = it.name,
            score = it.score,
            episodes = it.episodes,
            episodesAired = it.episodesAired,
            url = it.url,
            status = it.status,
            statusColor = MapperUtils.checkStatusColor(it.status),
            russian = it.russian
        )
    }
}

fun AnimePosterEntity.toLocalListAnimePosterEntity(): LocalAnimePosterEntity {
    return LocalAnimePosterEntity(
        id = this.id,
        image = this.image,
        name = this.name,
        score = this.score,
        episodes = this.episodes,
        episodesAired = this.episodesAired,
        url = this.url,
        status = this.status,
        russian = this.russian
    )
}

fun List<LocalAnimePosterEntity>.localToListAnimePosterEntity(): List<AnimePosterEntity> {
    return this.map {
        AnimePosterEntity(
            id = it.id,
            image = it.image,
            name = it.name,
            score = it.score,
            episodes = it.episodes,
            episodesAired = it.episodesAired,
            url = it.url,
            status = it.status,
            statusColor = MapperUtils.checkStatusColor(it.status),
            russian = it.russian
        )
    }
}

fun AnimePosterEntityResponse.toAnimePosterEntity(): AnimePosterEntity {
    return AnimePosterEntity(
        id = this.id,
        image = this.image.toImage(),
        name = this.name,
        score = this.score,
        episodes = this.episodes,
        episodesAired = this.episodesAired,
        url = this.url,
        status = this.status,
        statusColor = MapperUtils.checkStatusColor(this.status),
        russian = this.russian
    )
}

fun TranslationResponse.toTranslations(): Translations {
    return Translations(
        id = this.id,
        _kind = this._kind,
        targetId = this.targetId,
        episode = this.episode,
        url = this.url,
        hosting = this.hosting,
        language = this.language,
        author = this.author,
        _quality = this._quality,
        episodesTotal = this.episodesTotal
    )
}


fun TokenResponse.toToken() = Token(authToken = this.accessToken, refreshToken = this.refreshToken)

fun UserImageResponse.toUserImage() = UserImage(
    x160 = this.x160,
    x148 = this.x148,
    x80 = this.x80,
    x64 = this.x64,
    x48 = this.x48,
    x32 = this.x32,
    x16 = this.x16
)

fun UserBriefResponse.toUserBrief() = UserBrief(
    id = this.id,
    nickname = this.nickname,
    avatar = this.avatar,
    image = this.image.toUserImage(),
    name = this.name
)

fun FavoriteListResponse.toFavoriteList(): FavoriteList {
    with(this) {}
    val all = mutableListOf<Favorite>()
    val animes = animes.map { it.toFavorite(FavoriteType.ANIME) }
    val mangas = mangas.map { it.toFavorite(FavoriteType.MANGA) }
    val characters = characters.map { it.toFavorite(FavoriteType.CHARACTERS) }
    val people = people.map { it.toFavorite(FavoriteType.PEOPLE) }
    val mangakas = mangakas.map { it.toFavorite(FavoriteType.MANGAKAS) }
    val seyu = seyu.map { it.toFavorite(FavoriteType.SEYU) }
    val producers = producers.map { it.toFavorite(FavoriteType.PRODUCERS) }

    all.apply {
        addAll(animes)
        addAll(mangas)
        addAll(characters)
        addAll(people)
        addAll(mangakas)
        addAll(seyu)
        addAll(producers)
    }

    return FavoriteList(animes, mangas, characters, people, mangakas, seyu, producers, all)
}

fun FavoriteResponse.toFavorite(type: FavoriteType): Favorite = Favorite(
    this.id,
    this.name,
    this.nameRu,
    this.image.appendHostIfNeed().replace("x64", "original"),
    this.url?.appendHostIfNeed(),
    type
)

fun UserDetailsResponse.toUserDetails(): UserDetails = UserDetails(
    this.id,
    this.stats.toUserStats()
)

fun UserStatsResponse.toUserStats(): UserStats {
    with(this) {}
    val animes = status.anime.map { it.toStatus() }
    val mangas = status.manga.map { it.toStatus() }

    return UserStats(
        animes,
        mangas,
        scores.toStats(),
        types.toStats(),
        ratings.toStats(),
        hasAnime,
        hasManga
    )
}

fun StatResponse.toStats(): UserStat = UserStat(
    this.anime.map { it.toStatistic() },
    this.manga?.map { it.toStatistic() }
)


fun StatisticResponse.toStatistic(): Statistic = Statistic(this.name, this.value)

fun StatusResponse.toStatus(): Status = Status(this.id, this.name, this.size, this.type)

fun ImageResponse.toImage() =
    Image(
        original = this.original ?: "",
        preview = this.preview ?: "",
        x48 = this.x48 ?: "",
        x96 = this.x96 ?: ""
    )

fun RateResponse.toRate() = Rate(
    id = this.id,
    score = this.score,
    status = this.status,
    episodes = this.episodes,
    anime = this.anime?.toAnimePosterEntity()
)

fun List<RateResponse>.toListRates() = this.map { it.toRate() }

fun String.appendHostIfNeed(host: String = SHIKIMORI_URL): String {
    return if (this.contains("http")) this else host + this
}