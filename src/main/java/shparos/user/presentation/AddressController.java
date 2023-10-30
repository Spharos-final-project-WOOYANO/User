package shparos.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shparos.user.application.AddressService;
import shparos.user.application.UserService;
import shparos.user.domain.User;
import shparos.user.dto.AddressRegisterDto;
import shparos.user.vo.AddressOut;
import shparos.user.vo.AddressRegisterIn;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    /*
        주소리스트 조회
     */
    @Operation(summary = "주소리스트 조회", description = "등록되어 있는 주소리스트 전체 조회", tags = { "Address" })
    @GetMapping("/address")
    public ResponseEntity<List<AddressOut>> getAddressList(@RequestHeader("Authorization") String token) {

        // 토큰에서 유저정보 취득
        User user = userService.getUserFromToken(token);

        // 주소 리스트 조회
        List<AddressOut> addressOutList = addressService.getAddressList(user);
        return new ResponseEntity<>(addressOutList, HttpStatus.OK);
    }

    /*
        주소등록
     */
    @Operation(summary = "주소등록", description = "새로운 주소를 등록", tags = { "Address" })
    @PostMapping("/address")
    public ResponseEntity<String> registerAddress(@RequestHeader("Authorization") String token,
                                                  @RequestBody AddressRegisterIn addressRegisterIn) {

        // 토큰에서 유저정보 취득
        User user = userService.getUserFromToken(token);

        AddressRegisterDto addressRegisterDto = AddressRegisterDto.builder()
                .user(user)
                .localAddress(addressRegisterIn.getLocalAddress())
                .extraAddress(addressRegisterIn.getExtraAddress())
                .defaultAddress(addressRegisterIn.getDefaultAddress())
                .localCode(addressRegisterIn.getLocalCode())
                .build();

        // 주소 등록
        addressService.registerAddress(addressRegisterDto);

        return new ResponseEntity<>("주소등록", HttpStatus.OK);
    }

    /*
        주소수정
     */

    /*
        주소삭제
     */

    /*
        대표주소변경
     */


}
