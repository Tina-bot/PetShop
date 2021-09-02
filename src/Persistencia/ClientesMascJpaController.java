package Persistencia;

import Logica.ClientesMasc;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

//constructor de la jpa para manipular las querys de las entidades
public class ClientesMascJpaController implements Serializable {

    public ClientesMascJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
//le digo la persistencia que tiene que hacerle caso

    public ClientesMascJpaController() {
        emf = Persistence.createEntityManagerFactory("Petshop_PU");
    }

    public void create(ClientesMasc clientesMasc) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(clientesMasc);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientesMasc(clientesMasc.getNum_cliente()) != null) {
                throw new PreexistingEntityException("ClientesMasc " + clientesMasc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientesMasc clientesMasc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            clientesMasc = em.merge(clientesMasc);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = clientesMasc.getNum_cliente();
                if (findClientesMasc(id) == null) {
                    throw new NonexistentEntityException("The clientesMasc with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientesMasc clientesMasc;
            try {
                clientesMasc = em.getReference(ClientesMasc.class, id);
                clientesMasc.getNum_cliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientesMasc with id " + id + " no longer exists.", enfe);
            }
            em.remove(clientesMasc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClientesMasc> findClientesMascEntities() {
        return findClientesMascEntities(true, -1, -1);
    }

    public List<ClientesMasc> findClientesMascEntities(int maxResults, int firstResult) {
        return findClientesMascEntities(false, maxResults, firstResult);
    }

    private List<ClientesMasc> findClientesMascEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientesMasc.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ClientesMasc findClientesMasc(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientesMasc.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesMascCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientesMasc> rt = cq.from(ClientesMasc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
