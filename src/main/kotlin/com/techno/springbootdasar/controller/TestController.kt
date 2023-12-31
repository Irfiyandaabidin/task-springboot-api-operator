package com.techno.springbootdasar.controller

import com.github.javafaker.Faker
import com.techno.springbootdasar.domain.dto.request.ReqIdentitasDto
import com.techno.springbootdasar.domain.dto.request.ReqOperationDto
import com.techno.springbootdasar.domain.dto.response.ResBaseDto
import com.techno.springbootdasar.domain.dto.response.ResFullNameDto
import com.techno.springbootdasar.domain.dto.response.ResIdentitasDto
import com.techno.springbootdasar.domain.dto.response.ResOperationDto
import com.techno.springbootdasar.service.LogicService
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping("/v1/api")
class TestController(
  private val logicService: LogicService
) {
    val firstName = "irfiyanda"
    @Value("\${person.name.last}")
    val lastName = ""

    val log = Logger.getLogger(this::class.java.toString())

    @GetMapping("/test")
    fun testGetMapping(): ResponseEntity<LinkedHashMap<String, String>> {
        val response : LinkedHashMap<String, String> = LinkedHashMap()
        response["firstName"] = firstName
        response["lastName"] = lastName
        return ResponseEntity.ok(response)
    }

    @GetMapping("/get-age")
    fun getAge(@RequestParam("age") age : String) : ResponseEntity<LinkedHashMap<String, String>> {
        val response : LinkedHashMap<String, String> = LinkedHashMap()
        response["firstName"] = firstName
        response["lastName"] = lastName
        response["age"] = age
        return ResponseEntity.ok(response)
    }

    @GetMapping("/random/name")
    fun getNameRandom(@RequestParam("size") size : Int): ResponseEntity<ResBaseDto<ArrayList<ResBaseDto<ResFullNameDto>>>> {
        val response : LinkedHashMap<String, String> = LinkedHashMap()
        val data = ArrayList<ResBaseDto<ResFullNameDto>>()
        val faker = Faker()
        for(i in 1..size)
        {
            val first_name = faker.name().firstName().toString();
            val last_name = faker.name().lastName().toString();
            val age = faker.number().digit().toLong();

            val fullName = logicService.fullName(ReqIdentitasDto(first_name, last_name, age));
            data.add(fullName)
        }
        val responseBase = ResBaseDto(status = true, message = "success", data = data)
        return ResponseEntity.ok().body(responseBase)
    }

    @GetMapping("/get-age/{age}")
    fun getAgeByPath(@PathVariable("age") age : String): ResponseEntity<LinkedHashMap<String, String>> {
        val response : LinkedHashMap<String, String> = LinkedHashMap()
        response["firstName"] = firstName
        response["lastName"] = lastName
        response["age"] = age
        return ResponseEntity.ok(response)
    }

    @PostMapping("/get-identitas")
    fun getIdentitas(@RequestBody reqIdentitasDto: ReqIdentitasDto): ResponseEntity<ResBaseDto<ResIdentitasDto>> {
        log.info("Incoming request : $reqIdentitasDto")
//        val response : LinkedHashMap<String, String> = LinkedHashMap()
//        response["firstName"] = reqIdentitasDto.firstName.toString()
//        response["lastName"] = reqIdentitasDto.lastName.toString()
//        response["age"] = reqIdentitasDto.age.toString()

        val responseBody = ResIdentitasDto(
            firstName = reqIdentitasDto.firstName,
            lastName = reqIdentitasDto.lastName,
            age = reqIdentitasDto.age
        )

        val responseBase = ResBaseDto(status = true, message = "success", data = responseBody)
        return ResponseEntity.ok().body(responseBase)
    }

    @PostMapping("/get-fullname")
    fun getFullName(@RequestBody reqIdentitasDto: ReqIdentitasDto): ResponseEntity<ResBaseDto<ResFullNameDto>> {
        val response = logicService.fullName(reqIdentitasDto)
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/operation/{mathOperator}")
    fun operation(@RequestBody reqOperationDto: ReqOperationDto, @PathVariable("mathOperator") mathOperator : String ): ResponseEntity<ResBaseDto<ResOperationDto>> {
        val request = ReqOperationDto(
            number1= reqOperationDto.number1,
            number2 = reqOperationDto.number2,
            mathOperator = mathOperator
        )
        val response = logicService.operation(request)
        return ResponseEntity.ok().body(response);
    }
}
