package ics.qst.service;

import ics.qst.mapper.DeviceMapper;
import ics.qst.vo.DeviceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceService {

	@Autowired
	private DeviceMapper deviceMapper;

	public List<DeviceList> getDeviceGrpCdList() {

		return deviceMapper.getDeviceGrpCdList();
	}
	
	public List<DeviceList> setCdSelect(String listGrpCd) {
		
		return deviceMapper.setCdSelect(listGrpCd);
	}

	@Transactional(readOnly = true)
	public List<DeviceList> getServerUsage(String value) {
		
		return deviceMapper.getServerUsage(value);
	}

	@Transactional(readOnly = true)
	public List<DeviceList> getDevice(DeviceList deviceList) {
		
		return deviceMapper.getDevice(deviceList);
	}

	@Transactional(readOnly = true)
	public List<DeviceList> getGrpNmList(String value) {
		
		return deviceMapper.getGrpNmList(value);
	}

	@Transactional(readOnly = true)
	public List<String> getInstallationList() {

		return deviceMapper.getInstallationList();
	}

	@Transactional(readOnly = true)
	public List<DeviceList> getAllGrpNmList() {

		return deviceMapper.getAllGrpNmList();
	}

	@Transactional(readOnly = true)
    public List<DeviceList> getDeviceList(String value) {

		return deviceMapper.getDeviceList(value);
    }

	@Transactional
	public void addDevice(DeviceList deviceList) {
		if(!deviceList.getSelfGrpNm().isEmpty()) {
			deviceList.setCode( deviceMapper.getNewCode() );
			deviceList.setGrpCd( deviceMapper.getMaxGrpCd() );
			deviceList.setGrpNm( deviceList.getSelfGrpNm() );
		} else {
			deviceList.setCode( deviceMapper.getMaxCodeByGrpCd(deviceList) );
		}

		deviceMapper.addDevice(deviceList);
	}

	@Transactional(readOnly = true)
	public List<DeviceList> getDeviceListByGrpCdAndInstallation(DeviceList deviceList) {

		return deviceMapper.getDeviceListByGrpCdAndInstallation(deviceList);
	}

    public void saveDeviceList(List<DeviceList> deviceList) {
		DeviceList dlVo = new DeviceList();
		for(DeviceList dl : deviceList) {
			dlVo.setCode(dl.getCode());
			dlVo.setCodeNm(dl.getCodeNm());
			dlVo.setInstallation(dl.getInstallation());
			dlVo.setIp(dl.getIp());
			dlVo.setDeviceSpot(dl.getDeviceSpot());
			dlVo.setUseYn(dl.getUseYn());
			dlVo.setUseYnReason(dl.getUseYnReason());

			deviceMapper.saveDeviceList(dlVo);
		}
    }
}