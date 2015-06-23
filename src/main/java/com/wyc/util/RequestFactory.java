package com.wyc.controller.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestFactory {
	private Request getRequestByConnection(URL url) throws IOException {
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		Request request = new Request(httpURLConnection);
		return request;
	}

	public Request accessTokenRequest(String appid, String secret)
			throws IOException {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ appid + "&secret=" + secret);
		return getRequestByConnection(url);

	}

	public Request serveripsRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	public Request messageCustomRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// �ϴ�ͼ����Ϣ�زġ����ĺ���������֤������á�
	public Request uploadnewsRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ���ݷ������Ⱥ�������ĺ���������֤������á�
	public Request massSendallRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ������ƵȺ����Ϣ
	public Request uploadVideoRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ����OpenID�б�Ⱥ�������ĺŲ����ã��������֤����á���������ͼ����Ϣ���ı���������ͼƬ��
	public Request massSendRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ɾ��Ⱥ���ӿ�
	public Request massDeleteRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ��ָ���û�������Ϣ
	public Request massPreviewRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ��ȡ��Ϣ����
	public Request massGetRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ģ����Ϣ�ӿ�
	public Request templateSendRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ��������
	public Request groupsCreateRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/create?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ��ѯ���з���
	public Request groupsGetRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/get?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ��ѯ�û����ڷ���
	public Request groupsGetidRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/getid?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// �޸ķ�������
	public Request groupsUpdateRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/update?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// �ƶ��û�����
	public Request groupsMembersUpdateRequest(String accessToken)
			throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// �����ƶ��û�����
	public Request groupsMembersBatchUpdateRequest(String accessToken)
			throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// ɾ������
	public Request groupsDeleteeRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/delete?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}
	
	//�����û���ע��
	public Request userInfoUpdateRemarkRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}
	
	//��ȡ�û�������Ϣ
	public Request userInfoRequest(String accessToken,String openid,String lang)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openid+"&lang="
						+ lang);
		return getRequestByConnection(url);
	}
	
	//��ȡ�û��б� next_openid��һ����ȡ��openid������Ĭ�ϴӿ�ͷ��ʼ��ȡ
	public Request userGetRequest(String accessToken,String next_openid)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken+"&next_openid="
						+ next_openid);
		return getRequestByConnection(url);
	}
	
	//�Զ���˵������ӿ�
	public Request menuCreateRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//�Զ���˵���ѯ
	public Request menuGetRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//�Զ���˵�ɾ��
	public Request menuDeleteRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//�Զ���˵����ýӿ�
	public Request menuInfoRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//���ɴ������Ķ�ά��
	public Request qrcodeCreateRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//ͨ��ticket��ȡ��ά��
	public Request showQrcodeRequest(String ticket)throws Exception{
		URL url = new URL(
				"https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket);
		return getRequestByConnection(url);
	}
	
	//������ת�����ӽӿ�
	public Request shortUrlRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/shorturl?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//��ȡ�û���������
	public Request getusersummaryRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getusersummary?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//��ȡ�ۼ��û�����
	public Request getusercumulateRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getusercumulate?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//��ȡͼ��Ⱥ��ÿ������
	public Request getarticlesummaryRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getarticlesummary?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//��ȡͼ��Ⱥ��������
	public Request getarticletotalRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getarticletotal?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//��ȡͼ��ͳ������
	public Request getuserreadRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getuserread?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//��ȡͼ��ͳ�Ʒ�ʱ����
	public Request getuserreadhourRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getuserreadhour?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//��ȡͼ�ķ���ת������
	public Request getusershareRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getusershare?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//��ȡͼ�ķ���ת����ʱ����
	public Request getusersharehourRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getusersharehour?access_token="+accessToken);
		return getRequestByConnection(url);
	}
}
