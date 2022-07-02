package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        assertThat(messageCodes).containsExactly(
                "required.item.itemName"
                ,"required.itemName"
                ,"required.java.lang.String"
                ,"required" );
        /**
         * bindingResult.rejectValue("itemName","required");
         * rejectValue안에서 MessageCodesResolver를 호출한다.
         * new FieldError를 생성한다.
         * new FieldError("item" , "itemName" , null , false , messageCodes, null, null);
         *
         *
         * 객체 오류
         * 객체 오류의 경우 다음 순서로 2가지 생성
         * 1.: code + "." + object name
         * 2.: code
         * 예) 오류 코드: required, object name: item
         * 1.: required.item
         * 2.: required
         *
         *
         * 필드 오류
         * 필드 오류의 경우 다음 순서로 4가지 메시지 코드 생성
         * 1.: code + "." + object name + "." + field
         * 2.: code + "." + field
         * 3.: code + "." + field type
         * 4.: code
         *
         */
    }

}
