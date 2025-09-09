package br.com.joaovq.data.network.datasource

import br.com.joaovq.data.network.service.SpaceFlightNewsApi
import br.com.joaovq.testing.utils.Faker
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertTrue

class SpaceFlightRemoteDataSourceImplTest {
    @RelaxedMockK
    lateinit var spaceFlightApi: SpaceFlightNewsApi
    private lateinit var spaceFlightApiRemoteDataSource: SpaceFlightRemoteDataSource

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        spaceFlightApiRemoteDataSource =
            SpaceFlightRemoteDataSourceImpl(spaceFlightApi, UnconfinedTestDispatcher())
    }

    @Test
    fun `given id when getArticleById then return article`() = runTest {
        coEvery { spaceFlightApi.getArticleById(any()) } returns Response.success(Faker.article())
        val result = spaceFlightApiRemoteDataSource.getArticleById(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() != null)
    }
}