package shparos.user.address.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shparos.user.address.domain.Address;
import shparos.user.address.domain.UserAddressList;
import shparos.user.address.dto.AddressRegisterResultDto;
import shparos.user.address.infrastructure.UserAddressListRepository;
import shparos.user.global.common.response.ResponseCode;
import shparos.user.users.domain.User;
import shparos.user.address.dto.AddressModifyDto;
import shparos.user.address.dto.AddressRegisterDto;
import shparos.user.global.exception.CustomException;
import shparos.user.address.infrastructure.AddressRepository;
import shparos.user.address.vo.AddressDefaultOut;
import shparos.user.address.vo.AddressOut;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserAddressListRepository userAddressListRepository;

    // 주소리스트 조회
    @Override
    public List<AddressOut> getAddressList(User user) {

        // 해당하는 유저의 주소 정보 모두 조회
//        List<Address> addressList = addressRepository.findByUser(user);
//
//        return addressList.stream()
//                .map(address -> AddressOut.builder()
//                    .id(address.getId())
//                    .localAddress(address.getLocalAddress())
//                    .extraAddress(address.getExtraAddress())
////                    .defaultAddress(address.getDefaultAddress())
//                    .build())
//                .toList();
    return null;
    }

    // 주소등록
    @Override
    @Transactional
    public AddressRegisterResultDto registerAddress(AddressRegisterDto addressRegisterDto) {

        // 주소 등록
        Address address = Address.createAddress(addressRegisterDto.getLocalAddress(),
                addressRegisterDto.getExtraAddress(), addressRegisterDto.getLocalCode());
        addressRepository.save(address);

        // 대표주소인 경우
        if(addressRegisterDto.getDefaultAddress()) {
            // 기존 대표주소 조회
            UserAddressList userAddressList = userAddressListRepository.findByUserAndDefaultAddress(
                    addressRegisterDto.getUser(), Boolean.TRUE);

            if(userAddressList != null) {
                // false로 갱신
                userAddressList.setDefaultAddress(Boolean.FALSE);
            }
        }

        // 유저 주소 중간 테이블 등록
        UserAddressList newUserAddressList = UserAddressList.createUserAddressList(addressRegisterDto.getUser(),
                address, addressRegisterDto.getDefaultAddress());
        userAddressListRepository.save(newUserAddressList);

        return  AddressRegisterResultDto.builder()
                .localAddress(address.getLocalAddress())
                .extraAddress(address.getExtraAddress())
                .defaultAddress(newUserAddressList.getDefaultAddress())
                .localCode(address.getLocalCode())
                .build();
    }

    // 주소수정
    @Override
    @Transactional
    public void modifyAddress(AddressModifyDto addressModifyDto) {

        // 대표주소인 경우
//        if(addressModifyDto.getDefaultAddress()) {
//            // 기존 대표주소를 false로 갱신
//            Address defaultAddress = addressRepository.findByUserAndDefaultAddress(addressModifyDto.getUser(), true);
//            defaultAddress.setDefaultAddress(Boolean.FALSE);
//            addressRepository.save(defaultAddress);
//        }

        // 수정할 주소를 찾음
        Address modifyAddress = addressRepository.findById(addressModifyDto.getAddressId())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_ADDRESS));

        modifyAddress.update(addressModifyDto.getLocalAddress(),
                addressModifyDto.getExtraAddress(),
                addressModifyDto.getLocalCode());
    }

    // 주소삭제
    @Override
    @Transactional
    public void deleteAddress(Long addressId) {

        // 삭제할 주소 조회
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_ADDRESS));

        // 삭제할 주소가 대표 주소인 경우 에러
//        if(address.getDefaultAddress()) {
//            throw new CustomException(ResponseCode.CANNOT_DELETE_DEFAULT_ADDRESS);
//        }

        addressRepository.delete(address);
    }

    // 대표주소 조회
    @Override
    public AddressDefaultOut getDefaultAddress(User user) {

        // 대표주소 조회
//        Address address = addressRepository.findByUserAndDefaultAddress(user, Boolean.TRUE);

//        // 주소 정보가 없는 경우 에러
//        if(address == null) {
//            throw new CustomException(ResponseCode.CANNOT_FIND_ADDRESS);
//        }
//
//        return AddressDefaultOut.builder()
//                .id(address.getId())
//                .localAddress(address.getLocalAddress())
//                .extraAddress(address.getExtraAddress())
//                .defaultAddress(address.getDefaultAddress())
//                .localCode(address.getLocalCode())
//                .build();
    return null;
    }

}
