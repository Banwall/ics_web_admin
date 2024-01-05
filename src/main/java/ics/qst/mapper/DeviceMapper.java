package ics.qst.mapper;

import ics.qst.vo.DeviceList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceMapper {
	
	public List<DeviceList> getDeviceGrpCdList();

	public List<DeviceList> setCdSelect(String listGrpCd);

	public void saveDevice(DeviceList deviceList);

	public void addDevice(DeviceList deviceList);
	
	public void deleteDeviceStatus(String code);

	public void deleteDeviceList(String code);

	public List<DeviceList> getServerUsage(String value);

	public List<DeviceList> getChartData(String code);

	public List<DeviceList> getDevice(DeviceList deviceList);

	public List<DeviceList> getGrpNmList(String value);

	public List<DeviceList> getDataList(String value);

	public List<DeviceList> getChartDataList(String[] chkList);

	public List<String> getInstallationList();

	public List<DeviceList> getAllGrpNmList();

    public List<DeviceList> getDeviceList(String value);

	public int getMaxCodeByGrpCd(DeviceList deviceList);

	public String getMaxGrpCd();

	public int getNewCode();

	public List<DeviceList> getDeviceListByGrpCdAndInstallation(DeviceList deviceList);

    public void saveDeviceList(DeviceList dlVo);
}