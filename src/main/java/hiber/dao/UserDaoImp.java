package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User get(String model, int series) {
        Query<Long> carQuery = sessionFactory
                .getCurrentSession()
                .createQuery("select id from Car where model = :par1 AND series = :par2 ", Long.class);

        carQuery.setParameter("par1", model);
        carQuery.setParameter("par2", series);

        Query<User> userQuery = sessionFactory
                .getCurrentSession()
                .createQuery("from User where id = :par3", User.class);
        userQuery.setParameter("par3", carQuery.getSingleResult());
        return userQuery.getSingleResult();
    }


}
