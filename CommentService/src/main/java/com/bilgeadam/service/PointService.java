package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreatePointRequestDto;
import com.bilgeadam.dto.request.DeletePointRequestDto;
import com.bilgeadam.dto.request.UpdatePointRequestDto;
import com.bilgeadam.dto.response.UserForCommentServiceResponseDto;
import com.bilgeadam.entity.Point;
import com.bilgeadam.exception.CommentManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.IUserprofileManager;
import com.bilgeadam.mapper.IPointMapper;
import com.bilgeadam.repository.IPointRepository;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PointService extends ServiceManager<Point,String> {
    private final IPointRepository pointRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserprofileManager userprofileManager;

    public PointService(IPointRepository pointRepository, JwtTokenProvider jwtTokenProvider, IUserprofileManager userprofileManager) {
        super(pointRepository);
        this.pointRepository = pointRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userprofileManager = userprofileManager;
    }
    public Point createPoint(CreatePointRequestDto dto){
        String userprofileId = tokenToUserprofileWithControls(dto.getToken()).getId();
        Point point= IPointMapper.INSTANCE.toPoint(dto);
        Optional<Point> optionalPoint = pointRepository.findByUserprofileIdAndRecipeId(userprofileId,dto.getRecipeId());
        if (optionalPoint.isPresent()) {
            throw new CommentManagerException(ErrorType.POINT_ALREADY_EXIST);
        }
        point.setUserprofileId(userprofileId);
        return save(point);
    }
    public Point updatePoint(UpdatePointRequestDto dto){
        String userprofileId = tokenToUserprofileWithControls(dto.getToken()).getId();
        Optional<Point> pointOld = pointRepository.findById(dto.getId());
        if (!pointOld.get().getUserprofileId().equals(userprofileId)) {
            throw new CommentManagerException(ErrorType.AUTHORIZATION_ERROR); //todo add error userid are not match
        }
        if (pointOld.isEmpty()) {
            throw new CommentManagerException(ErrorType.USER_NOT_FOUND);
        }
        Point pointNew = IPointMapper.INSTANCE.toPoint(dto,pointOld.get());
        pointNew.setUserprofileId(userprofileId);
        return update(pointNew);
    }
    public Boolean deletePoint(DeletePointRequestDto dto){
        String userprofileId = tokenToUserprofileWithControls(dto.getToken()).getId();
        Optional<Point> pointOld = pointRepository.findById(dto.getId());
        if (!pointOld.get().getUserprofileId().equals(userprofileId)) {
            throw new CommentManagerException(ErrorType.AUTHORIZATION_ERROR); //todo add error userid are not match
        }
        delete(pointOld.get());
        return true;
    }

    /**
     * this method will be activated after front end ask the list or average of
     * points
     * @param recipeId --> which recipe you need
     * @return
     */
    // todo after project end change that to send infos to recipe as pointIds
    public List<Point> getRecipesPoints(String recipeId){
        return pointRepository.findAllByRecipeId(recipeId);
    }
    public UserForCommentServiceResponseDto tokenToUserprofileWithControls(String token){
        //Optional<String> role = jwtTokenProvider.getRoleFromToken(token);
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()) {
            throw new CommentManagerException(ErrorType.USER_NOT_FOUND);
        }
        UserForCommentServiceResponseDto dto = userprofileManager.getMyProfile(authId.get()).getBody();
        if (dto.getUsername().isEmpty()) {
            throw new CommentManagerException(ErrorType.USER_NOT_FOUND);
        }
        return dto;
    }
}
