package limia.Service

import limia.Dto.Relation
import java.util.*

/**
 * Created by workstation on 05/04/2017.
 */
interface IRelationService {

    fun create(firstEntityID: String, secondEntityID: String, firstEntityRole: String, secondEntityRole: String,
               relationName: String): Relation

    fun readByType(relationName : String): ArrayList<Relation>
    fun exists(relationName: String, firstRoleplayerID: String, secondRoleplayerID: String, firstRole: String,
               secondRole: String): Boolean?

    fun readByID(identifier: String): Relation?
    fun readAll(): ArrayList<Relation>
    fun deleteByID(identifier: String)
}
