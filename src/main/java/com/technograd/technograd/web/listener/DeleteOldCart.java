package com.technograd.technograd.web.listener;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class DeleteOldCart implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new DeleteOldCardCommand(), 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }

    private class DeleteOldCardCommand implements Runnable{

        private final int CART__EXPIRY_DATE = 10;
        private final IntendDAO intendDAO;
        public DeleteOldCardCommand() {
            intendDAO = new IntendDAO();
        }
        public DeleteOldCardCommand(IntendDAO intendDAO) {
            this.intendDAO = intendDAO;
        }
        @Override
        public void run() {
            List<Intend> intendList = new ArrayList<>();

            try{
                intendList = intendDAO.getAllCartsWithExpiredDateByDays(CART__EXPIRY_DATE);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }

            for (Intend i: intendList) {
                try {
                    intendDAO.deleteCartAndListIntendById(i.getId());
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
            }


        }
    }

}
