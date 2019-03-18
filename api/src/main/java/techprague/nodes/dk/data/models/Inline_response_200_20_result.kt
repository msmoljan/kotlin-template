/**
 * OpenDota API
 * # Introduction The OpenDota API provides Dota 2 related data including advanced match data extracted from match replays.  **Beginning 2018-04-22, the OpenDota API is limited to 50,000 free calls per month and 60 requests/minute** We offer a Premium Tier with unlimited API calls and higher rate limits. Check out the [API page](https://www.opendota.com/api-keys) to learn more.
 *
 * OpenAPI spec version: 17.6.1
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package techprague.nodes.dk.data.models

/**
 * result
 * @param gold_per_min
 * @param xp_per_min
 * @param kills_per_min
 * @param last_hits_per_min
 * @param hero_damage_per_min
 * @param hero_healing_per_min
 * @param tower_damage
 */
data class Inline_response_200_20_result(
    val gold_per_min: Array<Inline_response_200_20_result_gold_per_min>? = null,

    val xp_per_min: Array<Inline_response_200_20_result_gold_per_min>? = null,

    val kills_per_min: Array<Inline_response_200_20_result_gold_per_min>? = null,

    val last_hits_per_min: Array<Inline_response_200_20_result_gold_per_min>? = null,

    val hero_damage_per_min: Array<Inline_response_200_20_result_gold_per_min>? = null,

    val hero_healing_per_min: Array<Inline_response_200_20_result_gold_per_min>? = null,

    val tower_damage: Array<Inline_response_200_20_result_gold_per_min>? = null
)
