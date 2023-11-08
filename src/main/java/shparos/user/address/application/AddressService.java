package shparos.user.address.application;

import shparos.user.address.dto.AddressRegisterResultDto;
import shparos.user.users.domain.User;
import shparos.user.address.dto.AddressModifyDto;
import shparos.user.address.dto.AddressRegisterDto;
import shparos.user.address.vo.AddressDefaultResponse;
import shparos.user.address.vo.AddressResponse;

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

}
