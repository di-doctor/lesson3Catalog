package com.kiparo.lesson3.catalog.data

import com.kiparo.lesson3.catalog.model.Advertisement
import com.kiparo.lesson3.catalog.model.Catalog
import com.kiparo.lesson3.catalog.model.Product
import com.kiparo.lesson3.catalog.model.Video
import net.datafaker.Faker

private const val VIDEO_URL =
    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
private const val IMAGE_URL_ADV = "https://picsum.photos/id/%d/200/300"
private const val IMAGE_URL_PRODUCT = "https://picsum.photos/id/%d/200/300"
private const val IMAGE_URL_VIDEO = "https://picsum.photos/id/%d/500/300"

class DataSource {

    lateinit var observer: (items: List<Catalog>) -> Unit

    private val faker = Faker()
    private val advs = mutableListOf<Advertisement>()
    private val videos = mutableListOf<Video>()
    private val products = mutableListOf<Product>()
    private val catalog = mutableListOf<Catalog>()

    fun generate() {
        videos()
        advs()
        products()
        catalog
            .apply {
                this.addAll(advs)
                this.addAll(videos)
                this.addAll(products)
            }.apply {
                this.shuffle()
            }
    }

    fun getData(): List<Catalog> {
        return mutableListOf<Catalog>().apply {
            this.addAll(catalog)
        }
    }

    fun generateNext(advertisement: Advertisement) {
        TODO("Not yet implemented")
    }

    fun generateNext(product: Product) {
        TODO("Not yet implemented")
    }

    fun generateNext(video: Video) {
        val index = videos.indexOf(video)
        val catalogIndex = catalog.indexOf(video)
        val id = generateId(videos)
        val newVideo = createVideo(id + 1)
        videos.add(index + 1, newVideo)
        catalog.add(catalogIndex + 1, newVideo)
        observer(getData())
    }

    fun delete(product: Product) {
        products.remove(product)
        catalog.remove(product)
        observer(getData())
    }

    fun delete(advertisement: Advertisement) {
        TODO("Not yet implemented")
    }

    fun delete(video: Video) {
        TODO("Not yet implemented")
    }

    private fun generateId(videos: MutableList<Video>): Int {
        videos.sortBy { video ->
            video.id.filter { id ->
                id.isDigit()
            }.toInt()
        }
        val id = videos.last().id.filter {
            it.isDigit()
        }.toInt()
        return id
    }


    private fun videos() {
        videos.addAll(mutableListOf<Video>().apply {
            (1..30).forEach {
                this.add(
                    createVideo(it)
                )
            }
        })
    }

    private fun advs() {
        advs.addAll(mutableListOf<Advertisement>().apply {
            (1..30).forEach {
                this.add(
                    createAdvertisement(it)
                )
            }
        })
    }

    private fun products() {
        products.addAll(mutableListOf<Product>().apply {
            (1..30).forEach {
                this.add(
                    createProduct(it)
                )
            }
        })
    }

    private fun createAdvertisement(it: Int) = Advertisement(
        id = "$it-advs",
        imageUrl = String.format(IMAGE_URL_ADV, it),
        title = "AD: ${faker.funnyName().name()}",
        description = faker.text().text(50)
    )

    private fun createVideo(it: Int) = Video(
        id = "$it-video",
        videoUri = String.format(IMAGE_URL_VIDEO, it),
        title = "Video: ${faker.funnyName().name()}",
        description = faker.text().text(100)
    )

    private fun createProduct(it: Int) = Product(
        id = "$it-product",
        imageUrl = String.format(IMAGE_URL_PRODUCT, it),
        title = faker.animal().scientificName(),
        price = "${faker.number().digits(3)} ${faker.money().currency()}"
    )
}