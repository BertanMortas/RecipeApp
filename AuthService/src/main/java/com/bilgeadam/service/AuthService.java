package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.entity.Auth;
import com.bilgeadam.entity.enums.EStatus;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.IUserprofileManager;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.rabbitmq.model.ForgotPasswordModel;
import com.bilgeadam.rabbitmq.producer.MailForgotPasswordProducer;
import com.bilgeadam.rabbitmq.producer.MailRegisterProducer;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.bilgeadam.utility.CodeGenerator.generateCode;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailRegisterProducer mailRegisterProducer;
    private final MailForgotPasswordProducer mailForgotPasswordProducer;
    private final IUserprofileManager userprofileManager;

    public AuthService(IAuthRepository authRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, MailRegisterProducer mailRegisterProducer, MailForgotPasswordProducer mailForgotPasswordProducer, IUserprofileManager userprofileManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailRegisterProducer = mailRegisterProducer;
        this.mailForgotPasswordProducer = mailForgotPasswordProducer;
        this.userprofileManager = userprofileManager;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = save(IAuthMapper.INSTANCE.toAuth(dto));
        if (!dto.getPassword().equals(dto.getRePassword())) {
            throw new AuthManagerException(ErrorType.PASSWORD_ERROR);
        }
        auth.setActivationCode(generateCode());
        auth.setPassword(passwordEncoder.encode(dto.getPassword()));
        save(auth);
        mailRegisterProducer.sendActivationCode(IAuthMapper.INSTANCE.toModel(auth));// model to send mail service
        // send user to save
        userprofileManager.createUserprofile(IAuthMapper.INSTANCE.toRegisterDto(auth));
        return IAuthMapper.INSTANCE.toResponse(auth);
    }
    public Boolean activateStatus(StatusRequestDto dto){
        Optional<Auth> auth = authRepository.findById(dto.getId());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        // TODO Check status if deleted or banned throw error
       if (auth.get().getActivationCode().equals(dto.getActivationCode())){
           auth.get().setStatus(EStatus.ACTIVE);
           update(auth.get());
           // send user to update
           userprofileManager.updateStatus(jwtTokenProvider.createToken(auth.get().getId()).get());
           return true;
       }
        throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
    }
    public String login(LoginRequestDto dto){
        Optional<Auth> auth = authRepository.findByUsername(dto.getUsername());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(dto.getPassword(), auth.get().getPassword())) {
            throw new AuthManagerException(ErrorType.PASSWORD_ERROR);
        }
        if (!auth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
        Optional<String> token = jwtTokenProvider.createToken(auth.get().getId(),auth.get().getRole());
        return token.get();
    }
    public String forgotPassword(ForgotPasswordRequestDto dto){ // TODO after mailService change return type to Boolean
        Optional<Auth> auth = authRepository.findByUsername(dto.getUsername());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!auth.get().getEmail().equals(dto.getEmail())) { // TODO for safety do not throw an error
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!auth.get().getStatus().equals(EStatus.ACTIVE)){
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
        if (auth.get().getStatus().equals(EStatus.INACTIVE)){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        String newPassword = UUID.randomUUID().toString();
        newPassword = "A*"+newPassword; // for regex rules
        auth.get().setPassword(passwordEncoder.encode(newPassword));
        // TODO user update new password
        // mail send new password to user email
        mailForgotPasswordProducer.sendForgotPassword(ForgotPasswordModel.builder()
                        .email(auth.get().getEmail())
                        .username(auth.get().getUsername())
                .newPassword(newPassword).build());
        save(auth.get());
        return newPassword;
    }
    public Boolean updateAuth(UpdateAuthRequestDto dto){
        Optional<Auth> auth = authRepository.findById(dto.getAuthId());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        update(IAuthMapper.INSTANCE.updateAuthFromDto(dto,auth.get()));
        return true;
    }

    public void deleteStatus(Long authId) {
        Optional<Auth> auth = authRepository.findById(authId);
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setStatus(EStatus.INACTIVE);
        update(auth.get());
    }

    public Boolean updatePassword(ChangePasswordRequestDto dto) {
        System.out.println(dto.getToken());
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
        if (authId.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        Optional<Auth> auth = authRepository.findById(authId.get());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setPassword(dto.getNewPassword());
        update(auth.get());
        return true;
    }
}
