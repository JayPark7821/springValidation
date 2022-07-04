* ### BeanValidation
  * 검증순서
    * `@ModelAttribute` 각각의 필드에 타입 변환시도
      * 성공하면 다음으로
      * 실패하면 `typeMismastch`로 `FieldError` 추가
    * Validator 적용
      * >바인딩에 성공한 필드만 BeanValidation 적용!!!

* ### BeanValidation Groups
  * 예를들어 등록시에 검증할 기능과 수정시에 검증할 기능을 
  * 각각 그룹으로 나누어 적용할 수 있다.
```java
public class Item {

  @NotNull(groups = UpdateCheck.class)
  private Long id;
  @NotBlank(groups = {UpdateCheck.class, SaveCheck.class})
  private String itemName;
  @NotNull(groups = {UpdateCheck.class, SaveCheck.class})
  @Range(min = 1000, max = 1000000, groups = {UpdateCheck.class, SaveCheck.class})
  private Integer price;
  @NotNull(groups = {UpdateCheck.class, SaveCheck.class})
  @Max(value = 9999, groups = SaveCheck.class)
  private Integer quantity;
}
```