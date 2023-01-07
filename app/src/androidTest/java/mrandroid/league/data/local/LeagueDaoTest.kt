package mrandroid.league.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import mrandroid.league.data.local.entity.CompetitionEntity
import mrandroid.league.util.parser.GsonParser
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LeagueDaoTest {

    private lateinit var leagueDB: LeagueDB
    private lateinit var dao: LeagueDao

    @Before
    fun createDb() {
        leagueDB = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LeagueDB::class.java
        ).addTypeConverter(Converters(GsonParser(Gson()))).build()
        dao = leagueDB.dao
    }

    @After
    fun closeDb() {
        leagueDB.close()
    }

    @Test
    fun insertCompetition() = runTest {
        val competition1 = CompetitionEntity(
            id = 1, null, "comp_1", "123", "", "plan1", null,
            arrayListOf(), 3, "1/1/2023"
        )
        val competition2 = CompetitionEntity(
            id = 2, null, "comp_2", "123", "", "plan2", null,
            arrayListOf(), 3, "1/1/2023"
        )

        dao.insertCompetitions(listOf(competition1, competition2))
        val result = dao.getAllCompetitions()
        assertThat(result).contains(competition1)
    }

    @Test
    fun deleteCompetition() = runTest {
        val competition = CompetitionEntity(
            id = 1, null, "comp_1", "123", "", "plan1", null,
            arrayListOf(), 3, "1/1/2023"
        )
        dao.insertCompetition(competition)
        dao.deleteCompetition(competition)
        val result = dao.getCompetitionById(competition.id)
        assertThat(result).isNull()
    }

}