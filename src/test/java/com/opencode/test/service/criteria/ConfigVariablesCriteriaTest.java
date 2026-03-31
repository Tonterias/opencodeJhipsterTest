package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ConfigVariablesCriteriaTest {

    @Test
    void newConfigVariablesCriteriaHasAllFiltersNullTest() {
        var configVariablesCriteria = new ConfigVariablesCriteria();
        assertThat(configVariablesCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void configVariablesCriteriaFluentMethodsCreatesFiltersTest() {
        var configVariablesCriteria = new ConfigVariablesCriteria();

        setAllFilters(configVariablesCriteria);

        assertThat(configVariablesCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void configVariablesCriteriaCopyCreatesNullFilterTest() {
        var configVariablesCriteria = new ConfigVariablesCriteria();
        var copy = configVariablesCriteria.copy();

        assertThat(configVariablesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(configVariablesCriteria)
        );
    }

    @Test
    void configVariablesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var configVariablesCriteria = new ConfigVariablesCriteria();
        setAllFilters(configVariablesCriteria);

        var copy = configVariablesCriteria.copy();

        assertThat(configVariablesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(configVariablesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var configVariablesCriteria = new ConfigVariablesCriteria();

        assertThat(configVariablesCriteria).hasToString("ConfigVariablesCriteria{}");
    }

    private static void setAllFilters(ConfigVariablesCriteria configVariablesCriteria) {
        configVariablesCriteria.id();
        configVariablesCriteria.configVarLong1();
        configVariablesCriteria.configVarLong2();
        configVariablesCriteria.configVarLong3();
        configVariablesCriteria.configVarLong4();
        configVariablesCriteria.configVarLong5();
        configVariablesCriteria.configVarLong6();
        configVariablesCriteria.configVarLong7();
        configVariablesCriteria.configVarLong8();
        configVariablesCriteria.configVarLong9();
        configVariablesCriteria.configVarLong10();
        configVariablesCriteria.configVarLong11();
        configVariablesCriteria.configVarLong12();
        configVariablesCriteria.configVarLong13();
        configVariablesCriteria.configVarLong14();
        configVariablesCriteria.configVarLong15();
        configVariablesCriteria.configVarBoolean16();
        configVariablesCriteria.configVarBoolean17();
        configVariablesCriteria.configVarBoolean18();
        configVariablesCriteria.configVarString19();
        configVariablesCriteria.configVarString20();
        configVariablesCriteria.distinct();
    }

    private static Condition<ConfigVariablesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getConfigVarLong1()) &&
                condition.apply(criteria.getConfigVarLong2()) &&
                condition.apply(criteria.getConfigVarLong3()) &&
                condition.apply(criteria.getConfigVarLong4()) &&
                condition.apply(criteria.getConfigVarLong5()) &&
                condition.apply(criteria.getConfigVarLong6()) &&
                condition.apply(criteria.getConfigVarLong7()) &&
                condition.apply(criteria.getConfigVarLong8()) &&
                condition.apply(criteria.getConfigVarLong9()) &&
                condition.apply(criteria.getConfigVarLong10()) &&
                condition.apply(criteria.getConfigVarLong11()) &&
                condition.apply(criteria.getConfigVarLong12()) &&
                condition.apply(criteria.getConfigVarLong13()) &&
                condition.apply(criteria.getConfigVarLong14()) &&
                condition.apply(criteria.getConfigVarLong15()) &&
                condition.apply(criteria.getConfigVarBoolean16()) &&
                condition.apply(criteria.getConfigVarBoolean17()) &&
                condition.apply(criteria.getConfigVarBoolean18()) &&
                condition.apply(criteria.getConfigVarString19()) &&
                condition.apply(criteria.getConfigVarString20()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ConfigVariablesCriteria> copyFiltersAre(
        ConfigVariablesCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getConfigVarLong1(), copy.getConfigVarLong1()) &&
                condition.apply(criteria.getConfigVarLong2(), copy.getConfigVarLong2()) &&
                condition.apply(criteria.getConfigVarLong3(), copy.getConfigVarLong3()) &&
                condition.apply(criteria.getConfigVarLong4(), copy.getConfigVarLong4()) &&
                condition.apply(criteria.getConfigVarLong5(), copy.getConfigVarLong5()) &&
                condition.apply(criteria.getConfigVarLong6(), copy.getConfigVarLong6()) &&
                condition.apply(criteria.getConfigVarLong7(), copy.getConfigVarLong7()) &&
                condition.apply(criteria.getConfigVarLong8(), copy.getConfigVarLong8()) &&
                condition.apply(criteria.getConfigVarLong9(), copy.getConfigVarLong9()) &&
                condition.apply(criteria.getConfigVarLong10(), copy.getConfigVarLong10()) &&
                condition.apply(criteria.getConfigVarLong11(), copy.getConfigVarLong11()) &&
                condition.apply(criteria.getConfigVarLong12(), copy.getConfigVarLong12()) &&
                condition.apply(criteria.getConfigVarLong13(), copy.getConfigVarLong13()) &&
                condition.apply(criteria.getConfigVarLong14(), copy.getConfigVarLong14()) &&
                condition.apply(criteria.getConfigVarLong15(), copy.getConfigVarLong15()) &&
                condition.apply(criteria.getConfigVarBoolean16(), copy.getConfigVarBoolean16()) &&
                condition.apply(criteria.getConfigVarBoolean17(), copy.getConfigVarBoolean17()) &&
                condition.apply(criteria.getConfigVarBoolean18(), copy.getConfigVarBoolean18()) &&
                condition.apply(criteria.getConfigVarString19(), copy.getConfigVarString19()) &&
                condition.apply(criteria.getConfigVarString20(), copy.getConfigVarString20()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
