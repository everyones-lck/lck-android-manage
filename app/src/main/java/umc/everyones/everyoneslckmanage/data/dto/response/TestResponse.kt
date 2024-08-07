package umc.everyones.everyoneslckmanage.data.dto.response

import umc.everyones.everyoneslckmanage.domain.model.TestModel

data class TestResponse (
    val body: String
){
    fun toTestModel() = TestModel(body)
}