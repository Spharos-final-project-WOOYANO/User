package shparos.user.application;

import shparos.user.domain.User;
import shparos.user.dto.AddressModifyDto;
import shparos.user.dto.AddressRegisterDto;
import shparos.user.vo.AddressDefaultOut;
import shparos.user.vo.AddressOut;

import java.util.List;

public interface AddressService {

    // 주소리스트 조회
    List<AddressOut> getAddressList(User user);

    // 주소등록
    void registerAddress(AddressRegisterDto addressRegisterDto);

    // 주소수정
    void modifyAddress(AddressModifyDto addressModifyDto);

    // 주소삭제
    void deleteAddress(Long addressId);

    // 대표주소 조회
    AddressDefaultOut getDefaultAddress(User user);

}
