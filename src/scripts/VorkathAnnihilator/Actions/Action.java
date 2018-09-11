package scripts.VorkathAnnihilator.Actions;

import scripts.VorkathAnnihilator.Utils.ACamera;

public abstract class Action  {

    protected ACamera camera;


    public Action(ACamera camera) {
        this.camera = camera;
    }
    public abstract boolean active();
    public abstract void activate();

}
