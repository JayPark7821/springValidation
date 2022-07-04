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


* ### `@ModelAttribute vs @RequestBody`

> HTTP 요청 파리미터를 처리하는 `@ModelAttribute` 는 각각의 필드 단위로 세밀하게 적용된다. 
> 
>그래서 특정 필드에 타입이 맞지 않는 오류가 발생해도 나머지 필드는 정상 처리할 수 있었다.
>
>`HttpMessageConverter` 는 `@ModelAttribute` 와 다르게 각각의 필드 단위로 적용되는 것이 아니라, 전체 객체 단위로 적용된다.
>
> 따라서 메시지 컨버터의 작동이 성공해서 ItemSaveForm 객체를 만들어야 @Valid , @Validated 가 적용된다.
>
> `@ModelAttribute` 는 필드 단위로 정교하게 바인딩이 적용된다. 
> 특정 필드가 바인딩 되지 않아도 나머지  필드는 정상 바인딩 되고, `Validator`를 사용한 검증도 적용할 수 있다.
> 
> `@RequestBody` 는 `HttpMessageConverter` 단계에서 JSON 데이터를 객체로 변경하지 못하면 이후
> 
> 단계 자체가 진행되지 않고 예외가 발생한다. 컨트롤러도 호출되지 않고, `Validator`도 적용할 수 없다