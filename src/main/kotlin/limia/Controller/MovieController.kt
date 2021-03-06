package limia.Controller

import limia.Dto.Movie
import limia.Exception.EntityAlreadyExistsException
import limia.Exception.EntityNotFoundException
import limia.Exception.InvalidParametersException
import limia.Service.MovieService
import spark.Request
import java.util.*

/**
 * Created by macbook on 8/4/17.
 */
class MovieController {

    private val movieService: MovieService = MovieService()


    fun areOnCreateParamsCorrect(request: Request): Boolean {
        return request.queryParams().contains("themoviedb_id")
    }

    @Throws(EntityAlreadyExistsException::class, InvalidParametersException::class)
    fun createMovie(request: Request): Movie {

        if (!areOnCreateParamsCorrect(request)) {
            var exception = InvalidParametersException()
            exception.addParameter("themoviedb_id")
            throw exception
        }

        val themoviedb_id = request.queryParams("themoviedb_id")
        return movieService.create(themoviedb_id)
    }

    @Throws(EntityNotFoundException::class)
    fun findMovie(request: Request): Movie {
        return movieService.read(request.params(":id"))
    }

    @Throws(EntityNotFoundException::class)
    fun deleteMovie(request: Request) {
        movieService.delete(request.params(":id"))
    }


    /*
    fun updateMovie(request: Request) {
        val movie = Movie()
        if (request.queryParams().contains("themoviedb_id")) movie.themoviedb_id = request.queryParams("themoviedb_id")
        movie.identifier = request.params(":id")
        movieService.update(movie)
    }
    */

    fun readAllMovies(): ArrayList<Movie> {
        return movieService.readAll()
    }

}
