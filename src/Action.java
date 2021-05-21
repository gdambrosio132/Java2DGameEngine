import java.util.ArrayList;
import java.util.Arrays;

public class Action {
    public interface Function{
        boolean run(Sprite target, double deltaTime, double totalTime);
    }

    public Function function;
    public double totalTime;

    public Action(){
        totalTime = 0;
    }

    public Action(Function f){
        function = f;
        totalTime = 0;
    }

    public boolean apply(Sprite target, double deltaTime){
        totalTime += deltaTime;
        return function.run(target, deltaTime, totalTime);
    }

    public void reset(){
        totalTime = 0;
    }

    //Static functions to create Action objects
    public static Action moveBy(double deltaX, double deltaY, double duration){
        return new Action(
                (Sprite target, double deltaTime, double totalTime) -> {
                    target.moveBy(deltaX / duration * deltaTime, deltaY / duration * deltaTime);
                    return (totalTime >= duration);
                }
        );
    }

    public static Action rotateBy(double deltaAngle, double duration){
        return new Action(
                (Sprite target, double deltaTime, double totalTime) -> {
                    target.rotateBy(deltaAngle / duration * deltaTime);
                    return (totalTime >= duration);
                }
        );
    }

    public static Action fadeOut(double duration){
        return new Action(
                (Sprite target, double deltaTime, double totalTime) -> {
                    target.opacity -= (1 / duration * deltaTime);
                    if (target.opacity < 0){
                        target.opacity = 0;
                    }
                    return (totalTime >= duration);
                }
        );
    }

    public static Action removeSelf(){
        return new Action(
                (Sprite target, double dt, double tt) -> {
                    target.removeSelf();
                    return true;
                }
        );
    }

    public static Action isAnimationFinished(){
        return new Action(
                (Sprite target, double dt, double tt) -> {
                    return target.animation.isFinished();
                }
        );
    }

    //Create Meta-Actions
    public static Action repeat(Action action, int totalTimes){
        return new Action(){
            int finishedTimes = 0;

            @Override
            public boolean apply(Sprite target, double deltaTime){
                boolean finished = action.apply(target, deltaTime);
                if (finished){
                    finishedTimes += 1;
                    action.reset();
                }
                return (finishedTimes == totalTimes);
            }
        };
    }

    public static Action forever(Action action){
        return new Action(){
            @Override
            public boolean apply(Sprite target, double deltaTime){
                boolean finished = action.apply(target, deltaTime);
                if (finished){
                    action.reset();
                }
                return false;
            }
        };
    }

    public static Action sequence(Action... actions){
        return new Action(){
            ArrayList<Action> actionList = new ArrayList<Action>(Arrays.asList(actions));
            int currentIndex;

            @Override
            public boolean apply(Sprite target, double deltaTime){
                Action currentAction = actionList.get(currentIndex);
                boolean finished = currentAction.apply(target, deltaTime);
                if (finished){
                    currentIndex++;
                }
                return (currentIndex == actionList.size());
            }

            @Override
            public void reset(){
                for (Action action : actionList){
                    action.reset();
                }
                currentIndex = 0;
            }
        };
    }

    public static Action delay(double duration){
        return new Action(
                (Sprite target, double deltaTime, double totalTime) -> {
                    return (totalTime >= duration);
                }
        );
    }

    //Screen-based Actions
    public static Action boundToScreen(int screenWidth, int screenHeight){
        return new Action(
                (Sprite target, double deltaTime, double totalTime) -> {
                    target.boundToScreen(screenWidth, screenHeight);
                    return false;
                }
        );
    }

    public static Action wrapToScreen(int screenWidth, int screenHeight){
        return new Action(
                (Sprite target, double deltaTime, double totalTime) -> {
                    target.wrapToScreen(screenWidth, screenHeight);
                    return false;
                }
        );
    }

    //compound actions
    public static Action delayFadeRemove(double delay, double fadeTime){
        return Action.sequence(
                Action.delay(delay),
                Action.fadeOut(fadeTime),
                Action.removeSelf()
        );
    }

    public static Action animateThenRemove(){
        return Action.sequence(
                Action.isAnimationFinished(),
                Action.removeSelf()
        );
    }
}
