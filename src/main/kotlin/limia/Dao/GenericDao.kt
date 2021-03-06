package limia.Dao

import limia.Dto.Entity
import limia.Dto.Relation
import limia.Exception.EntityAlreadyExistsException
import limia.Exception.EntityNotFoundException
import limia.Grakn.GraknEntityManager
import java.lang.reflect.ParameterizedType
import java.util.*
import kotlin.reflect.KClass


/**
 * Created by workstation on 05/04/2017.
 */
@SuppressWarnings("unchecked")
abstract class GenericDao<T : Any> : IGenericDao<T> {

    private var type: Class<*>? = null
    private var graknEntityManager: GraknEntityManager? = null

    @Throws(EntityAlreadyExistsException::class)
    override fun create(t: T): T {
        val e = t as Entity
        if (graknEntityManager!!.exists(t.javaClass, e.identifier))
            throw EntityAlreadyExistsException()
        else
            return graknEntityManager!!.persist(t)
    }

    @Throws(Exception::class)
    override fun <T : Any> existsBy(type: KClass<T>, key: String, value: String) {
        val entity = graknEntityManager!!.findBy(type.java, key, value)
        if (entity != null)
            throw EntityAlreadyExistsException()
        else {
            val e = EntityNotFoundException()
            e.addEntityType(type)
            throw e
        }
    }

    override fun update(t: T): T {
        return graknEntityManager!!.update(t)
        //To change body of created functions use File | Settings | File Templates.
    }

    @Throws(EntityNotFoundException::class)
    override fun <T : Any> read(type: KClass<T>, id: Any): Any? {

        if (type != Relation::class && !graknEntityManager!!.exists(Entity::class.java, id)) {
            val e = EntityNotFoundException()
            e.addEntityType(type)
            throw e
        }
        if (type == Relation::class && !graknEntityManager!!.existsRelation(id)) {
            val e = EntityNotFoundException()
            e.addEntityType(type)
            throw e
        }
        return graknEntityManager!!.read(type.java, id)
    }

    fun existsRelation(relationName: String, firstRoleplayerID: String, secondRolePlayerID: String,
                       firstRole: String, secondRole: String): Boolean {
        return graknEntityManager!!.existsRelation(relationName, firstRoleplayerID, secondRolePlayerID, firstRole, secondRole)
    }

    @Throws(EntityNotFoundException::class)
    override fun <T : Any> delete(type: KClass<T>, id: Any) {
        if (type != Relation::class && !graknEntityManager!!.exists(Entity::class.java, id)) {
            val e = EntityNotFoundException()
            e.addEntityType(type)
            throw e
        }
        if (type == Relation::class && !graknEntityManager!!.existsRelation(id)) {
            val e = EntityNotFoundException()
            e.addEntityType(type)
            throw e
        }
        graknEntityManager!!.delete(type.java, id)
    }

    override fun readAll(type: Class<T>): ArrayList<T> {
        return graknEntityManager!!.readAll(type)
    }

    fun readRelationsByType(type: String): ArrayList<T> {
        return graknEntityManager!!.readRelationsByType(type)
    }


    init {
        graknEntityManager = GraknEntityManager()
        val t = javaClass.genericSuperclass
        val pt = t as ParameterizedType
        type = pt.actualTypeArguments[0] as Class<*>
    }


}
