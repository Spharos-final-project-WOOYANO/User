package spharos.user.address.application;

import spharos.user.address.dto.AddressRegisterResultDto;
import spharos.user.users.domain.User;
import spharos.user.address.dto.AddressModifyDto;
import spharos.user.address.dto.AddressRegisterDto;
import spharos.user.address.vo.AddressDefaultResponse;
import spharos.user.address.vo.AddressResponse;

import java.util.List;

public interface AddressService {

    // 주소리스트 조회
    List<AddressResponse> getAddressList(User user);

    // 주소등록
    AddressRegisterResultDto registerAddress(AddressRegisterDto addressRegisterDto);

    // 주소수정
    void modifyAddress(AddressModifyDto addressModifyDto);

    // 주소삭제
    void deleteAddress(Long addressId);

    // 대표주소 조회
    AddressDefaultResponse getDefaultAddress(User user);

    // 대표주소 변경
    void modifyDefaultAddress(User user, Long addressId);

}
