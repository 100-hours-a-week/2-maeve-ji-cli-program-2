package src;

public class MonsterAutoAttackThread extends Thread {
    private final long autoAttackInterval; // 몬스터별 자동공격 간격
    private volatile boolean running = true;
    private final Monster monster;
    private final Player player;

    // 플레이어의 행동 잡히는 시간 확인
    private volatile long lastPlayerActionTime;

    public MonsterAutoAttackThread(Monster monster, Player player, long autoAttackInterval) {
        this.monster = monster;
        this.player = player;
        this.autoAttackInterval = autoAttackInterval;
        this.lastPlayerActionTime = System.currentTimeMillis();
    }

    // 플레이어가 행동할 때마다 시간을 갱신
    public void updateLastActionTime() {
        this.lastPlayerActionTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        System.out.println("\n[System] 몬스터 자동 공격 스레드 시작! (" + monster.getName() + ", 간격: "
                + autoAttackInterval/1000 + "초)\n");

        while (running) {
            try {
                // 1초마다 체크
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 플레이어 or 몬스터 죽었으면 종료
            if (!player.isAlive() || !monster.isAlive()) {
                stopThread();
                break;
            }

            long now = System.currentTimeMillis();
            if (now - lastPlayerActionTime >= autoAttackInterval) {
                System.out.println("\n----------- 몬스터 자동 공격! -----------");
                System.out.println("[AutoAttack] " + monster.getName() + "이(가) 자동 공격을 시도합니다!");
                monster.attack(player, false);

                // 공격 후 플레이어가 죽었나 확인
                if (!player.isAlive()) {
                    System.out.println("[AutoAttack] " + player.getName() + "이(가) 쓰러졌습니다...");
                    stopThread();
                    break;
                }

                // 공격 후에는 타이머 초기화
                lastPlayerActionTime = System.currentTimeMillis();
                System.out.println("----------------------------------------\n");
            }
        }

        System.out.println("[System] 몬스터 자동 공격 스레드 종료 (" + monster.getName() + ")");
    }

    public void stopThread() {
        running = false;
    }
}
