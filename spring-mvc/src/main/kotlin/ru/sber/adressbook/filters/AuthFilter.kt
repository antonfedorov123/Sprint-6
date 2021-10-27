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
        val auth = req
            .cookies
            ?.find { x -> x.name.equals("auth") }
            ?.value

        val isCorrect = auth != null && auth.toLong() < Instant.now().epochSecond

        if (isCorrect) {
            chain.doFilter(req, res)
        } else {
            LOG.info("Ошибка при авторизации авторизация")
            res.sendRedirect("/login")
        }
    }

    companion object {
        val LOG: Logger = Logger.getLogger(LogFilter::class.java.name)
    }
}

