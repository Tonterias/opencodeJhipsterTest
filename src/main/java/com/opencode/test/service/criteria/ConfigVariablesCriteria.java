package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.ConfigVariables} entity. This class is used
 * in {@link com.opencode.test.web.rest.ConfigVariablesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /config-variables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfigVariablesCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter configVarLong1;

    private LongFilter configVarLong2;

    private LongFilter configVarLong3;

    private LongFilter configVarLong4;

    private LongFilter configVarLong5;

    private LongFilter configVarLong6;

    private LongFilter configVarLong7;

    private LongFilter configVarLong8;

    private LongFilter configVarLong9;

    private LongFilter configVarLong10;

    private LongFilter configVarLong11;

    private LongFilter configVarLong12;

    private LongFilter configVarLong13;

    private LongFilter configVarLong14;

    private LongFilter configVarLong15;

    private BooleanFilter configVarBoolean16;

    private BooleanFilter configVarBoolean17;

    private BooleanFilter configVarBoolean18;

    private StringFilter configVarString19;

    private StringFilter configVarString20;

    private Boolean distinct;

    public ConfigVariablesCriteria() {}

    public ConfigVariablesCriteria(ConfigVariablesCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.configVarLong1 = other.optionalConfigVarLong1().map(LongFilter::copy).orElse(null);
        this.configVarLong2 = other.optionalConfigVarLong2().map(LongFilter::copy).orElse(null);
        this.configVarLong3 = other.optionalConfigVarLong3().map(LongFilter::copy).orElse(null);
        this.configVarLong4 = other.optionalConfigVarLong4().map(LongFilter::copy).orElse(null);
        this.configVarLong5 = other.optionalConfigVarLong5().map(LongFilter::copy).orElse(null);
        this.configVarLong6 = other.optionalConfigVarLong6().map(LongFilter::copy).orElse(null);
        this.configVarLong7 = other.optionalConfigVarLong7().map(LongFilter::copy).orElse(null);
        this.configVarLong8 = other.optionalConfigVarLong8().map(LongFilter::copy).orElse(null);
        this.configVarLong9 = other.optionalConfigVarLong9().map(LongFilter::copy).orElse(null);
        this.configVarLong10 = other.optionalConfigVarLong10().map(LongFilter::copy).orElse(null);
        this.configVarLong11 = other.optionalConfigVarLong11().map(LongFilter::copy).orElse(null);
        this.configVarLong12 = other.optionalConfigVarLong12().map(LongFilter::copy).orElse(null);
        this.configVarLong13 = other.optionalConfigVarLong13().map(LongFilter::copy).orElse(null);
        this.configVarLong14 = other.optionalConfigVarLong14().map(LongFilter::copy).orElse(null);
        this.configVarLong15 = other.optionalConfigVarLong15().map(LongFilter::copy).orElse(null);
        this.configVarBoolean16 = other.optionalConfigVarBoolean16().map(BooleanFilter::copy).orElse(null);
        this.configVarBoolean17 = other.optionalConfigVarBoolean17().map(BooleanFilter::copy).orElse(null);
        this.configVarBoolean18 = other.optionalConfigVarBoolean18().map(BooleanFilter::copy).orElse(null);
        this.configVarString19 = other.optionalConfigVarString19().map(StringFilter::copy).orElse(null);
        this.configVarString20 = other.optionalConfigVarString20().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ConfigVariablesCriteria copy() {
        return new ConfigVariablesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getConfigVarLong1() {
        return configVarLong1;
    }

    public Optional<LongFilter> optionalConfigVarLong1() {
        return Optional.ofNullable(configVarLong1);
    }

    public LongFilter configVarLong1() {
        if (configVarLong1 == null) {
            setConfigVarLong1(new LongFilter());
        }
        return configVarLong1;
    }

    public void setConfigVarLong1(LongFilter configVarLong1) {
        this.configVarLong1 = configVarLong1;
    }

    public LongFilter getConfigVarLong2() {
        return configVarLong2;
    }

    public Optional<LongFilter> optionalConfigVarLong2() {
        return Optional.ofNullable(configVarLong2);
    }

    public LongFilter configVarLong2() {
        if (configVarLong2 == null) {
            setConfigVarLong2(new LongFilter());
        }
        return configVarLong2;
    }

    public void setConfigVarLong2(LongFilter configVarLong2) {
        this.configVarLong2 = configVarLong2;
    }

    public LongFilter getConfigVarLong3() {
        return configVarLong3;
    }

    public Optional<LongFilter> optionalConfigVarLong3() {
        return Optional.ofNullable(configVarLong3);
    }

    public LongFilter configVarLong3() {
        if (configVarLong3 == null) {
            setConfigVarLong3(new LongFilter());
        }
        return configVarLong3;
    }

    public void setConfigVarLong3(LongFilter configVarLong3) {
        this.configVarLong3 = configVarLong3;
    }

    public LongFilter getConfigVarLong4() {
        return configVarLong4;
    }

    public Optional<LongFilter> optionalConfigVarLong4() {
        return Optional.ofNullable(configVarLong4);
    }

    public LongFilter configVarLong4() {
        if (configVarLong4 == null) {
            setConfigVarLong4(new LongFilter());
        }
        return configVarLong4;
    }

    public void setConfigVarLong4(LongFilter configVarLong4) {
        this.configVarLong4 = configVarLong4;
    }

    public LongFilter getConfigVarLong5() {
        return configVarLong5;
    }

    public Optional<LongFilter> optionalConfigVarLong5() {
        return Optional.ofNullable(configVarLong5);
    }

    public LongFilter configVarLong5() {
        if (configVarLong5 == null) {
            setConfigVarLong5(new LongFilter());
        }
        return configVarLong5;
    }

    public void setConfigVarLong5(LongFilter configVarLong5) {
        this.configVarLong5 = configVarLong5;
    }

    public LongFilter getConfigVarLong6() {
        return configVarLong6;
    }

    public Optional<LongFilter> optionalConfigVarLong6() {
        return Optional.ofNullable(configVarLong6);
    }

    public LongFilter configVarLong6() {
        if (configVarLong6 == null) {
            setConfigVarLong6(new LongFilter());
        }
        return configVarLong6;
    }

    public void setConfigVarLong6(LongFilter configVarLong6) {
        this.configVarLong6 = configVarLong6;
    }

    public LongFilter getConfigVarLong7() {
        return configVarLong7;
    }

    public Optional<LongFilter> optionalConfigVarLong7() {
        return Optional.ofNullable(configVarLong7);
    }

    public LongFilter configVarLong7() {
        if (configVarLong7 == null) {
            setConfigVarLong7(new LongFilter());
        }
        return configVarLong7;
    }

    public void setConfigVarLong7(LongFilter configVarLong7) {
        this.configVarLong7 = configVarLong7;
    }

    public LongFilter getConfigVarLong8() {
        return configVarLong8;
    }

    public Optional<LongFilter> optionalConfigVarLong8() {
        return Optional.ofNullable(configVarLong8);
    }

    public LongFilter configVarLong8() {
        if (configVarLong8 == null) {
            setConfigVarLong8(new LongFilter());
        }
        return configVarLong8;
    }

    public void setConfigVarLong8(LongFilter configVarLong8) {
        this.configVarLong8 = configVarLong8;
    }

    public LongFilter getConfigVarLong9() {
        return configVarLong9;
    }

    public Optional<LongFilter> optionalConfigVarLong9() {
        return Optional.ofNullable(configVarLong9);
    }

    public LongFilter configVarLong9() {
        if (configVarLong9 == null) {
            setConfigVarLong9(new LongFilter());
        }
        return configVarLong9;
    }

    public void setConfigVarLong9(LongFilter configVarLong9) {
        this.configVarLong9 = configVarLong9;
    }

    public LongFilter getConfigVarLong10() {
        return configVarLong10;
    }

    public Optional<LongFilter> optionalConfigVarLong10() {
        return Optional.ofNullable(configVarLong10);
    }

    public LongFilter configVarLong10() {
        if (configVarLong10 == null) {
            setConfigVarLong10(new LongFilter());
        }
        return configVarLong10;
    }

    public void setConfigVarLong10(LongFilter configVarLong10) {
        this.configVarLong10 = configVarLong10;
    }

    public LongFilter getConfigVarLong11() {
        return configVarLong11;
    }

    public Optional<LongFilter> optionalConfigVarLong11() {
        return Optional.ofNullable(configVarLong11);
    }

    public LongFilter configVarLong11() {
        if (configVarLong11 == null) {
            setConfigVarLong11(new LongFilter());
        }
        return configVarLong11;
    }

    public void setConfigVarLong11(LongFilter configVarLong11) {
        this.configVarLong11 = configVarLong11;
    }

    public LongFilter getConfigVarLong12() {
        return configVarLong12;
    }

    public Optional<LongFilter> optionalConfigVarLong12() {
        return Optional.ofNullable(configVarLong12);
    }

    public LongFilter configVarLong12() {
        if (configVarLong12 == null) {
            setConfigVarLong12(new LongFilter());
        }
        return configVarLong12;
    }

    public void setConfigVarLong12(LongFilter configVarLong12) {
        this.configVarLong12 = configVarLong12;
    }

    public LongFilter getConfigVarLong13() {
        return configVarLong13;
    }

    public Optional<LongFilter> optionalConfigVarLong13() {
        return Optional.ofNullable(configVarLong13);
    }

    public LongFilter configVarLong13() {
        if (configVarLong13 == null) {
            setConfigVarLong13(new LongFilter());
        }
        return configVarLong13;
    }

    public void setConfigVarLong13(LongFilter configVarLong13) {
        this.configVarLong13 = configVarLong13;
    }

    public LongFilter getConfigVarLong14() {
        return configVarLong14;
    }

    public Optional<LongFilter> optionalConfigVarLong14() {
        return Optional.ofNullable(configVarLong14);
    }

    public LongFilter configVarLong14() {
        if (configVarLong14 == null) {
            setConfigVarLong14(new LongFilter());
        }
        return configVarLong14;
    }

    public void setConfigVarLong14(LongFilter configVarLong14) {
        this.configVarLong14 = configVarLong14;
    }

    public LongFilter getConfigVarLong15() {
        return configVarLong15;
    }

    public Optional<LongFilter> optionalConfigVarLong15() {
        return Optional.ofNullable(configVarLong15);
    }

    public LongFilter configVarLong15() {
        if (configVarLong15 == null) {
            setConfigVarLong15(new LongFilter());
        }
        return configVarLong15;
    }

    public void setConfigVarLong15(LongFilter configVarLong15) {
        this.configVarLong15 = configVarLong15;
    }

    public BooleanFilter getConfigVarBoolean16() {
        return configVarBoolean16;
    }

    public Optional<BooleanFilter> optionalConfigVarBoolean16() {
        return Optional.ofNullable(configVarBoolean16);
    }

    public BooleanFilter configVarBoolean16() {
        if (configVarBoolean16 == null) {
            setConfigVarBoolean16(new BooleanFilter());
        }
        return configVarBoolean16;
    }

    public void setConfigVarBoolean16(BooleanFilter configVarBoolean16) {
        this.configVarBoolean16 = configVarBoolean16;
    }

    public BooleanFilter getConfigVarBoolean17() {
        return configVarBoolean17;
    }

    public Optional<BooleanFilter> optionalConfigVarBoolean17() {
        return Optional.ofNullable(configVarBoolean17);
    }

    public BooleanFilter configVarBoolean17() {
        if (configVarBoolean17 == null) {
            setConfigVarBoolean17(new BooleanFilter());
        }
        return configVarBoolean17;
    }

    public void setConfigVarBoolean17(BooleanFilter configVarBoolean17) {
        this.configVarBoolean17 = configVarBoolean17;
    }

    public BooleanFilter getConfigVarBoolean18() {
        return configVarBoolean18;
    }

    public Optional<BooleanFilter> optionalConfigVarBoolean18() {
        return Optional.ofNullable(configVarBoolean18);
    }

    public BooleanFilter configVarBoolean18() {
        if (configVarBoolean18 == null) {
            setConfigVarBoolean18(new BooleanFilter());
        }
        return configVarBoolean18;
    }

    public void setConfigVarBoolean18(BooleanFilter configVarBoolean18) {
        this.configVarBoolean18 = configVarBoolean18;
    }

    public StringFilter getConfigVarString19() {
        return configVarString19;
    }

    public Optional<StringFilter> optionalConfigVarString19() {
        return Optional.ofNullable(configVarString19);
    }

    public StringFilter configVarString19() {
        if (configVarString19 == null) {
            setConfigVarString19(new StringFilter());
        }
        return configVarString19;
    }

    public void setConfigVarString19(StringFilter configVarString19) {
        this.configVarString19 = configVarString19;
    }

    public StringFilter getConfigVarString20() {
        return configVarString20;
    }

    public Optional<StringFilter> optionalConfigVarString20() {
        return Optional.ofNullable(configVarString20);
    }

    public StringFilter configVarString20() {
        if (configVarString20 == null) {
            setConfigVarString20(new StringFilter());
        }
        return configVarString20;
    }

    public void setConfigVarString20(StringFilter configVarString20) {
        this.configVarString20 = configVarString20;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConfigVariablesCriteria that = (ConfigVariablesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(configVarLong1, that.configVarLong1) &&
            Objects.equals(configVarLong2, that.configVarLong2) &&
            Objects.equals(configVarLong3, that.configVarLong3) &&
            Objects.equals(configVarLong4, that.configVarLong4) &&
            Objects.equals(configVarLong5, that.configVarLong5) &&
            Objects.equals(configVarLong6, that.configVarLong6) &&
            Objects.equals(configVarLong7, that.configVarLong7) &&
            Objects.equals(configVarLong8, that.configVarLong8) &&
            Objects.equals(configVarLong9, that.configVarLong9) &&
            Objects.equals(configVarLong10, that.configVarLong10) &&
            Objects.equals(configVarLong11, that.configVarLong11) &&
            Objects.equals(configVarLong12, that.configVarLong12) &&
            Objects.equals(configVarLong13, that.configVarLong13) &&
            Objects.equals(configVarLong14, that.configVarLong14) &&
            Objects.equals(configVarLong15, that.configVarLong15) &&
            Objects.equals(configVarBoolean16, that.configVarBoolean16) &&
            Objects.equals(configVarBoolean17, that.configVarBoolean17) &&
            Objects.equals(configVarBoolean18, that.configVarBoolean18) &&
            Objects.equals(configVarString19, that.configVarString19) &&
            Objects.equals(configVarString20, that.configVarString20) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            configVarLong1,
            configVarLong2,
            configVarLong3,
            configVarLong4,
            configVarLong5,
            configVarLong6,
            configVarLong7,
            configVarLong8,
            configVarLong9,
            configVarLong10,
            configVarLong11,
            configVarLong12,
            configVarLong13,
            configVarLong14,
            configVarLong15,
            configVarBoolean16,
            configVarBoolean17,
            configVarBoolean18,
            configVarString19,
            configVarString20,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigVariablesCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalConfigVarLong1().map(f -> "configVarLong1=" + f + ", ").orElse("") +
            optionalConfigVarLong2().map(f -> "configVarLong2=" + f + ", ").orElse("") +
            optionalConfigVarLong3().map(f -> "configVarLong3=" + f + ", ").orElse("") +
            optionalConfigVarLong4().map(f -> "configVarLong4=" + f + ", ").orElse("") +
            optionalConfigVarLong5().map(f -> "configVarLong5=" + f + ", ").orElse("") +
            optionalConfigVarLong6().map(f -> "configVarLong6=" + f + ", ").orElse("") +
            optionalConfigVarLong7().map(f -> "configVarLong7=" + f + ", ").orElse("") +
            optionalConfigVarLong8().map(f -> "configVarLong8=" + f + ", ").orElse("") +
            optionalConfigVarLong9().map(f -> "configVarLong9=" + f + ", ").orElse("") +
            optionalConfigVarLong10().map(f -> "configVarLong10=" + f + ", ").orElse("") +
            optionalConfigVarLong11().map(f -> "configVarLong11=" + f + ", ").orElse("") +
            optionalConfigVarLong12().map(f -> "configVarLong12=" + f + ", ").orElse("") +
            optionalConfigVarLong13().map(f -> "configVarLong13=" + f + ", ").orElse("") +
            optionalConfigVarLong14().map(f -> "configVarLong14=" + f + ", ").orElse("") +
            optionalConfigVarLong15().map(f -> "configVarLong15=" + f + ", ").orElse("") +
            optionalConfigVarBoolean16().map(f -> "configVarBoolean16=" + f + ", ").orElse("") +
            optionalConfigVarBoolean17().map(f -> "configVarBoolean17=" + f + ", ").orElse("") +
            optionalConfigVarBoolean18().map(f -> "configVarBoolean18=" + f + ", ").orElse("") +
            optionalConfigVarString19().map(f -> "configVarString19=" + f + ", ").orElse("") +
            optionalConfigVarString20().map(f -> "configVarString20=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
