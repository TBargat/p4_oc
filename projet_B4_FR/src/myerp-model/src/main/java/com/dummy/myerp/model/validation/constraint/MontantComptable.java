package com.dummy.myerp.model.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Digits;


/**
 * Constraints on the attributes being "montant comptable"
 *
 *  This constraint has :
 *  <ul>
 *      <li>@{@link Digits}</li>
 *  </ul>
 *
 *  Handled types :
 *  <ul>
 *      <li>{@link java.math.BigDecimal}</li>
 *  </ul>
 */
@Digits(integer = 13, fraction = 2)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface MontantComptable {

    /** Violation message */
    String message() default "Taux de TVA invalide";

    /** Validation Group */
    Class<?>[] groups() default {};

    /** Payload */
    Class<? extends Payload>[] payload() default {};

    /**
     * Interface to enable the declaration of several {@link MontantComptable}
     */
    @Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        /** List of {@link MontantComptable} */
        MontantComptable[] value();
    }
}
