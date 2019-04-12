package techprague.nodes.dk.data.models.body

import techprague.nodes.dk.data.models.Chat
import techprague.nodes.dk.data.models.MyWordCounts
import techprague.nodes.dk.data.models.Objective
import techprague.nodes.dk.data.models.PicksBan
import techprague.nodes.dk.data.models.Player

/**
 *
 * @param match_id The ID number of the match assigned by Valve
 * @param barracks_status_dire Bitmask. An integer that represents a binary of which barracks are still standing. 63 would mean all barracks still stand at the end of the game.
 * @param barracks_status_radiant Bitmask. An integer that represents a binary of which barracks are still standing. 63 would mean all barracks still stand at the end of the game.
 * @param chat Array containing information on the chat of the game
 * @param cluster cluster
 * @param cosmetics cosmetics
 * @param dire_score Final score for Dire (number of kills on Radiant)
 * @param draft_timings draft_timings
 * @param duration Duration of the game in seconds
 * @param engine engine
 * @param first_blood_time Time in seconds at which first blood occurred
 * @param game_mode Integer corresponding to game mode played. List of constants can be found here: https://github.com/odota/dotaconstants/blob/master/json/game_mode.json
 * @param human_players Number of human players in the game
 * @param leagueid leagueid
 * @param lobby_type Integer corresponding to lobby type of match. List of constants can be found here: https://github.com/odota/dotaconstants/blob/master/json/lobby_type.json
 * @param match_seq_num match_seq_num
 * @param negative_votes Number of negative votes the replay received in the in-game client
 * @param objectives objectives
 * @param picks_bans Object containing information on the draft. Each pick/ban contains a boolean relating to whether the choice is a pick or a ban, the hero ID, the team the picked or banned it, and the order.
 * @param positive_votes Number of positive votes the replay received in the in-game client
 * @param radiant_gold_adv Array of the Radiant gold advantage at each minute in the game. A negative number means that Radiant is behind, and thus it is their gold disadvantage.
 * @param radiant_score Final score for Radiant (number of kills on Radiant)
 * @param radiant_win Boolean indicating whether Radiant won the match
 * @param radiant_xp_adv Array of the Radiant experience advantage at each minute in the game. A negative number means that Radiant is behind, and thus it is their experience disadvantage.
 * @param start_time The Unix timestamp at which the game started
 * @param teamfights teamfights
 * @param tower_status_dire Bitmask. An integer that represents a binary of which Dire towers are still standing.
 * @param tower_status_radiant Bitmask. An integer that represents a binary of which Radiant towers are still standing.
 * @param version Parse version, used internally by OpenDota
 * @param replay_salt replay_salt
 * @param series_id series_id
 * @param series_type series_type
 * @param radiant_team radiant_team
 * @param dire_team dire_team
 * @param league league
 * @param skill Skill bracket assigned by Valve (Normal, High, Very High)
 * @param players Array of information on individual players
 * @param patch Information on the patch version the game is played on
 * @param region Integer corresponding to the region the game was played on
 * @param all_word_counts Word counts of the all chat messages in the player's games
 * @param my_word_counts Word counts of the player's all chat messages
 * @param _throw Maximum gold advantage of the player's team if they lost the match
 * @param comeback Maximum gold disadvantage of the player's team if they won the match
 * @param loss Maximum gold disadvantage of the player's team if they lost the match
 * @param win Maximum gold advantage of the player's team if they won the match
 * @param replay_url replay_url
 */

data class MatchById(
    val all_word_counts: Map<String, Int>,
    val barracks_status_dire: Int,
    val barracks_status_radiant: Int,
    val chat: List<Chat>,
    val cluster: Int,
    val dire_score: Int,
    val dire_team_id: Any?,
    val duration: Int,
    val engine: Int,
    val first_blood_time: Int,
    val game_mode: Int,
    val human_players: Int,
    val leagueid: Int,
    val lobby_type: Int,
    val loss: Int,
    val match_id: Long,
    val match_seq_num: Long,
    val my_word_counts: MyWordCounts,
    val negative_votes: Int,
    val objectives: List<Objective>,
    val patch: Int,
    val picks_bans: List<PicksBan>,
    val players: List<Player>,
    val positive_votes: Int,
    val radiant_gold_adv: List<Int>,
    val radiant_score: Int,
    val radiant_team_id: Any?,
    val radiant_win: Boolean,
    val radiant_xp_adv: List<Int>,
    val region: Int,
    val replay_salt: Int,
    val replay_url: String,
    val series_id: Int,
    val series_type: Int,
    val start_time: Int,
    val `throw`: Int,
    val tower_status_dire: Int,
    val tower_status_radiant: Int,
    val version: Int
)