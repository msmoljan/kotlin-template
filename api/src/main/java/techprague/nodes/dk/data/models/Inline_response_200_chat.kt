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
 *
 * @param time Time in seconds at which the message was said
 * @param unit Name of the player who sent the message
 * @param key The message the player sent
 * @param slot slot
 * @param player_slot Which slot the player is in. 0-127 are Radiant, 128-255 are Dire
 */
data class Inline_response_200_chat(
    /* Time in seconds at which the message was said */
    val time: Int? = null,

    /* Name of the player who sent the message */
    val unit: String? = null,

    /* The message the player sent */
    val key: String? = null,

    /* slot */
    val slot: Int? = null,

    /* Which slot the player is in. 0-127 are Radiant, 128-255 are Dire */
    val player_slot: Int? = null
)
