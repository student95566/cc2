package io.github.scitia.susieserver.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories(value = {
        "io.github.scitia.susieserver.backlog",
        "io.github.scitia.susieserver.backlog",
        "io.github.scitia.susieserver.comment",
        "io.github.scitia.susieserver.commitment",
        "io.github.scitia.susieserver.dictionary",
        "io.github.scitia.susieserver.issue",
        "io.github.scitia.susieserver.project",
        "io.github.scitia.susieserver.sprint",
        "io.github.scitia.susieserver.shared"})
@ComponentScan(value = {
        "io.github.scitia.susieserver.backlog",
        "io.github.scitia.susieserver.backlog",
        "io.github.scitia.susieserver.comment",
        "io.github.scitia.susieserver.commitment",
        "io.github.scitia.susieserver.dictionary",
        "io.github.scitia.susieserver.issue",
        "io.github.scitia.susieserver.project",
        "io.github.scitia.susieserver.sprint",
        "io.github.scitia.susieserver.shared"})
@EntityScan(value = {"io.github.lizewskik.susieserver.backlog",
        "io.github.scitia.susieserver.backlog",
        "io.github.scitia.susieserver.comment",
        "io.github.scitia.susieserver.commitment",
        "io.github.scitia.susieserver.dictionary",
        "io.github.scitia.susieserver.issue",
        "io.github.scitia.susieserver.project",
        "io.github.scitia.susieserver.sprint",
        "io.github.scitia.susieserver.shared"})
@AutoConfigureDataJpa
@SpringBootConfiguration
public class TestConfiguration {
}
