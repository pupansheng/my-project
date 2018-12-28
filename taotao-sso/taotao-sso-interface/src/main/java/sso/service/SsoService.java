package sso.service;

import taotao.common.utils.E3Result;
import taotao.manager.pojo.TbUser;

public interface SsoService {
public E3Result checkData(String param,String type);
public E3Result createUser(TbUser user);
public E3Result login(String username,String password);
public E3Result getUserByToken(String token);
}
