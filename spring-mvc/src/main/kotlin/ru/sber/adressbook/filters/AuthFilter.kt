package ru.sber.adressbook.filters

import org.springframework.core.annotation.Order
import java.time.Instant
import java.util.logging.Logger
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order(2)
@WebFilter("/app/*")
class AuthFilter: HttpFilter() {

    override fun doFilter(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {

        val auth = req.cookies
            ?.find { x -> x.name.equals("auth") }
            ?.value

        if (auth != null && auth.toLong() > Instant.now().epochSecond - 60 * 60 * 24) {
            chain.doFilter(req, res)
            return
        }

        LOG.info("Ошибка при авторизации")
        res.sendRedirect("/login")
    }

    companion object {
        val LOG: Logger = Logger.getLogger(LogFilter::class.java.name)
    }

}

