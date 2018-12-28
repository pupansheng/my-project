package taotao.order.service;

import taotao.common.utils.E3Result;
import taotao.order.pojo.OrderInfo;

public interface OrderService {
public E3Result creatOrder(OrderInfo info);
}
