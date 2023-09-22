package HR_Manager.Jwt;


//import com.java.springclasses.jwt.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//@RequiredArgsConstructor // final fields jo hoti hen unka constructor bana leta hia ye
public class JwtAuthenticationFilter extends OncePerRequestFilter {
//I think this one is better to use as compared to filterr of drugesh
    // get jwt
    // check for beARER
    // validate the token
    @Autowired
    private  JwtService jwtService;
//    private final JwtService jwtService;

    @Autowired
    private  UserDetailsService userDetailsService;
//    private final UserDetailsService userDetailsService;
//    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException
    {
        final String requestAuthHeader = request.getHeader("Authorization"); // q k jo req ayegi usme token ka headaer yahi hoga
        final String jwt;
        final String userEmail;

        //
        if (requestAuthHeader == null ||!requestAuthHeader.startsWith("Bearer "))   // check krne klie k request me token h ya nai , agar nai hai to bas hm age forward krdenge
        {
            System.out.println("filter chala bager token k");
            filterChain.doFilter(request, response);
            return;
        }
        jwt = requestAuthHeader.substring(7); //q k "Bearer " k baad likha hai token to islie hamne uske baad ka string save krlia hmne
       //jwt se related exceptions lagae ja skti hen as in filterr by durgesh
        System.out.println("ane wala token  "+jwt);
        userEmail = jwtService.extractUsername(jwt); // ye jwt token ko decode kr k nikaalna hoga hmen
//        userEmail = jwtService.extractUsernameFromToken(jwt); // ye jwt token ko decode kr k nikaalna hoga hmen
        System.out.println("email "+ userEmail);

        // vlidation process
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
        //hmen null k sath ye b check krna hia k kia user pehle se authenticated hai ya nai , null ka matlab k user abhi authenticated nia hua hai
        // chaunke authenticated nai ahi  , to hmen authenticatino krni pregi puri
        {
            System.out.println("useremail is not null");
            System.out.println("token k lie user credentials loadkra rhe hn");
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // ye jo userdetail service hai jis se load krrhe hen ham , that is bassicaly costume userdetail service , lekin isbne ne lambda k zariye kia hai to isko custom wala banaen ki zarurt nai prhi
            //app config me hai ye above line
//      var isTokenValid = tokenRepository.findByToken(jwt)
//          .map(t -> !t.isExpired() && !t.isRevoked())
//          .orElse(false); // ye wali validity durgesh nenai lagae
            // upr wala validity check is not in user currently
            if (jwtService.isTokenValid(jwt, userDetails) /*&& isTokenValid ye ham upr wali teen lines k sath lilkehnge*/ )
            { // agar to token valid hai to bas phir ham user me token save krayenge
                // ye object
                System.out.println("token is valid");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, // null q dia? , q k hmare object k pass nia hai
                        userDetails.getAuthorities()
                );
                // what is usermame password authentication token?
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                        //
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // authenticate kr k user ko context me save krwadenge
            }
        }else{
            System.out.println("Token is not validated or username null");                    //                .addFilterBefore(jwtAuthFilter,  UsernamePasswordAuthenticationFilter.class)

        }
//        System.out.println("pehle se authenticated hai");
        filterChain.doFilter(request, response);
    }
}

