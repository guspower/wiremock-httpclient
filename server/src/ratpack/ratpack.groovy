import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    all {
      response.status 204
      response.send()
    }
  }
}
