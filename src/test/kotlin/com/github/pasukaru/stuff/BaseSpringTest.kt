package com.github.pasukaru.stuff

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ContextConfiguration
@ActiveProfiles("test")
//@Transactional
@WebAppConfiguration
@EnableSpringDataWebSupport
abstract class BaseSpringTest