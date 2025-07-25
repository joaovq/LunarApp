package br.com.joaovq.lunarappcompose.data.network.datasource

import br.com.joaovq.lunarappcompose.data.network.service.SpaceFlightNewsApi
import br.com.joaovq.lunarappcompose.utils.Faker
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertTrue

class SpaceFlightRemoteDataSourceImplTest {
    @RelaxedMockK
    lateinit var spaceFlightApi: SpaceFlightNewsApi
    private lateinit var spaceFlightApiRemoteDataSource: SpaceFlightRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        spaceFlightApiRemoteDataSource = SpaceFlightRemoteDataSourceImpl(spaceFlightApi)
    }

    @Test
    fun `given id when getArticleById then return article`() = runTest {
        coEvery { spaceFlightApi.getArticleById(any()) } returns Response.success(Faker.article())

        val result = spaceFlightApiRemoteDataSource.getArticleById(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() != null)
    }
}