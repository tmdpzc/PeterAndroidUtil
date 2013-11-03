#Android Watcher

## Android Watcher框架是一个论寻监控的框架

> Andorid 中的 有些事件系统是不支持回调通知的，这种情况下需要通过论寻的方式来管理这些监控行为 。Watcher提供了这么一套机制来监控各种事件。用户只需要关心Wacher的两个基本接口，doWatch和dispatchEvent即可。Watcher 与 asyncui 的核心框架协同工作，Watcher监控到的事件可以直接通知到任何Android的组件中。

*生命周期
>WatcherManager 提供完整的生命周期控制，可以在用户解锁屏幕后开启，用户锁定屏幕后停止，不会影响系统的耗电情况。最小的论寻周期是1秒钟，不建议太频繁，后期可能会加入delta队列的功能支持进一步提高性能。

* Example 

```java
..................增加Watcher...........................
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		wm = Ruler.wm();
		wm.addWatcher(new Watcher(0, "simple1") {

			@Override
			public Event doWatch() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void dispatchEvent(Event event) {
				// TODO Auto-generated method stub

			}
		});
	}
.................控制..................
@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt1:
			wm.start();
			break;
		case R.id.bt2:
			wm.pause();
			break;
		case R.id.bt3:
			wm.resume();
			break;
		case R.id.bt4:
			wm.stop();
			break;

		default:
			break;
		}
	}

```


* 实现
> 有两个实现类WatcherManagerHandlerImpl和WatcherManagerImpl，一个是使用HandlerThread实现的，一个是使用Timer实现的，Handler实现的效率更高一点，而Timer实现的可能可以被用在其他Java环境中。

* 问题
> 需要对内部的定时周期做优化

* 其他
> 写这个东东的初衷是因为在公司的项目中总是需要监控各种事件
























