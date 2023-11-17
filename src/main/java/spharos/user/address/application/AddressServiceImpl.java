package spharos.user.address.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.user.address.domain.Address;
import spharos.user.address.domain.UserAddressList;
import spharos.user.address.dto.AddressRegisterResultDto;
import spharos.user.address.infrastructure.UserAddressListRepository;
import spharos.user.address.vo.AddressDetailResponse;
import spharos.user.global.common.response.ResponseCode;
import spharos.user.users.domain.User;
import spharos.user.address.dto.AddressModifyDto;
import spharos.user.address.dto.AddressRegisterDto;
import spharos.user.global.exception.CustomException;
import spharos.user.address.infrastructure.AddressRepository;
import spharos.user.address.vo.AddressDefaultResponse;
import spharos.user.address.vo.AddressResponse;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserAddressListRepository userAddressListRepository;

    // 주소리스트 조회
    @Override
    public List<AddressResponse> getAddressList(User user) {

        // 유저 주소 중간 테이블 조회
        List<UserAddressList> userAddressList = userAddressListRepository.findByUser(user);

        // 주소 정보가 없는 경우 null은 반환
        if (userAddressList.isEmpty()) {
            return null;
        }

        List<AddressResponse> responseList = new ArrayList<>();

        for(UserAddressList userAddress : userAddressList) {
            AddressResponse addressResponse = AddressResponse.builder()
                    .id(userAddress.getAddress().getId())
                    .localAddress(userAddress.getAddress().getLocalAddress())
                    .extraAddress(userAddress.getAddress().getExtraAddress())
                    .defaultAddress(userAddress.getDefaultAddress())
                    .build();
            responseList.add(addressResponse);
        }

    return responseList;
    }

    // 상세 주소 조회(수정페이지표시용)
    @Override
    public AddressDetailResponse getAddressDetail(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_ADDRESS));

        return AddressDetailResponse.builder()
                .id(address.getId())
                .localAddress(address.getLocalAddress())
                .extraAddress(address.getExtraAddress())
                .build();
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

        // 유저 주소 중간테이블 조회
        UserAddressList userAddressList = userAddressListRepository.findByAddress(address);

        // 삭제할 주소가 대표 주소인 경우 에러
        if(userAddressList.getDefaultAddress()) {
            throw new CustomException(ResponseCode.CANNOT_DELETE_DEFAULT_ADDRESS);
        }

        // 유저 주소 중간테이블 삭제
        userAddressListRepository.delete(userAddressList);

        // 주소 삭제
        addressRepository.delete(address);
    }

    // 대표주소 조회
    @Override
    public AddressDefaultResponse getDefaultAddress(User user) {

        // 유저 주소 중간 테이블에서 대표주소 조회
        UserAddressList userAddressList = userAddressListRepository.findByUserAndDefaultAddress(
                user, Boolean.TRUE);

        // 주소 정보가 없는 경우 에러
        if(userAddressList == null) {
            throw new CustomException(ResponseCode.CANNOT_FIND_ADDRESS);
        }

        return AddressDefaultResponse.builder()
                .id(userAddressList.getAddress().getId())
                .localAddress(userAddressList.getAddress().getLocalAddress())
                .extraAddress(userAddressList.getAddress().getExtraAddress())
                .defaultAddress(userAddressList.getDefaultAddress())
                .localCode(userAddressList.getAddress().getLocalCode())
                .build();
    }

    // 대표주소 변경
    @Override
    @Transactional
    public void modifyDefaultAddress(User user, Long addressId) {

        // 기존 대표주소였던 주소를 일반주소로 수정
        UserAddressList userAddressList = userAddressListRepository.findByUserAndDefaultAddress(user, Boolean.TRUE);
        userAddressList.setDefaultAddress(Boolean.FALSE);

        // 대표주소를 변경할 주소를 조회
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_ADDRESS));

        // 대표주소를 변경
        UserAddressList newDefaultAddress = userAddressListRepository.findByAddress(address);
        newDefaultAddress.setDefaultAddress(Boolean.TRUE);
    }

}
