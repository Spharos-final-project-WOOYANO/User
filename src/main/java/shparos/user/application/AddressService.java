package shparos.user.application;

import shparos.user.domain.User;
import shparos.user.dto.AddressRegisterDto;
import shparos.user.vo.AddressOut;

import java.util.List;

public interface AddressService {

    // 주소리스트 조회
    List<AddressOut> getAddressList(User user);

    // 주소등록
    void registerAddress(AddressRegisterDto addressRegisterDto);



}
