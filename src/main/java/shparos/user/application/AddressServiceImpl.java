package shparos.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shparos.user.domain.Address;
import shparos.user.domain.User;
import shparos.user.dto.AddressRegisterDto;
import shparos.user.infrastructure.AddressRepository;
import shparos.user.vo.AddressOut;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    // 주소리스트 조회
    @Override
    public List<AddressOut> getAddressList(User user) {

        // 해당하는 유저의 주소 정보 모두 조회
        List<Address> addressList = addressRepository.findByUser(user);

        return addressList.stream()
                .map(address -> AddressOut.builder()
                    .id(address.getId())
                    .localAddress(address.getLocalAddress())
                    .extraAddress(address.getExtraAddress())
                    .defaultAddress(address.getDefaultAddress())
                    .build())
                .toList();
    }

    // 주소등록
    @Override
    @Transactional
    public void registerAddress(AddressRegisterDto addressRegisterDto) {

        // 대표주소인 경우
        if(addressRegisterDto.getDefaultAddress()) {
            // 기존 대표주소를 false로 갱신
            Address defaultAddress = addressRepository.findByUserAndDefaultAddress(addressRegisterDto.getUser(), true);
            defaultAddress.setDefaultAddress(Boolean.FALSE);
                addressRepository.save(defaultAddress);
        }

        // 주소등록
        Address newAddress = Address.builder()
                .user(addressRegisterDto.getUser())
                .localAddress(addressRegisterDto.getLocalAddress())
                .extraAddress(addressRegisterDto.getExtraAddress())
                .defaultAddress(addressRegisterDto.getDefaultAddress())
                .localCode(addressRegisterDto.getLocalCode())
                .build();
        addressRepository.save(newAddress);
    }
}
